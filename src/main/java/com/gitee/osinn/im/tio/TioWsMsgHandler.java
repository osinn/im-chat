package com.gitee.osinn.im.tio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gitee.osinn.im.constants.Const;
import com.gitee.osinn.im.message.entity.ImMessage;
import com.gitee.osinn.im.message.service.IImMessageService;
import com.gitee.osinn.im.push.entity.Message;
import com.gitee.osinn.im.push.entity.SendInfo;
import com.gitee.osinn.im.sys.IImUserService;
import com.gitee.osinn.im.sys.ImUser;
import com.gitee.osinn.im.sys.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.core.TioConfig;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.http.common.HttpResponseStatus;
import org.tio.utils.lock.SetWithLock;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.common.WsResponse;
import org.tio.websocket.server.handler.IWsMsgHandler;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * websocket 处理函数
 *
 * @author 乐天
 * @since 2018-10-08
 */
@Component
public class TioWsMsgHandler implements IWsMsgHandler {

    private static Logger log = LoggerFactory.getLogger(TioWsMsgHandler.class);

    @Resource
    @Qualifier(value = "imUserService")
    private IImUserService imUserService;

    @Resource
    @Qualifier(value = "iImMessageService")
    private IImMessageService iImMessageService;

    @Autowired
    private RedisService redisService;

    public static TioConfig tioConfig;

    public static String TO_ID = "user_id#";


    /**
     * 握手时走这个方法，业务可以在这里获取cookie，request参数等
     *
     * @param request        request
     * @param httpResponse   httpResponse
     * @param channelContext channelContext
     * @return HttpResponse
     */
    @Override
    public HttpResponse handshake(HttpRequest request, HttpResponse httpResponse, ChannelContext channelContext) {
        String token = request.getParam("token");

        try {
            if (token != null) {
                tioConfig = channelContext.tioConfig;
                ImUser imUser = (ImUser) redisService.get(token);
                if (imUser == null) {
                    // 登陆认证失败
                    httpResponse.setStatus(HttpResponseStatus.getHttpStatus(401));
                } else {
                    Long id = imUserService.getByLoginName(imUser.getLoginName()).getId();
                    //绑定用户
                    Tio.bindUser(channelContext, TioWsMsgHandler.TO_ID + id);
                    // 在线用户绑定到上下文 用于发送在线消息
                    WsOnlineContext.bindUser(TioWsMsgHandler.TO_ID + id, channelContext);
                }
            } else {
                // 登陆认证失败
                httpResponse.setStatus(HttpResponseStatus.getHttpStatus(401));
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            // 登陆认证失败
            httpResponse.setStatus(HttpResponseStatus.getHttpStatus(401));
        }
        return httpResponse;
    }

    /**
     * @param httpRequest    httpRequest
     * @param httpResponse   httpResponse
     * @param channelContext channelContext
     * @throws Exception Exception
     * @author tanyaowu tanyaowu
     */
    @Override
    public void onAfterHandshaked(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {

    }

    /**
     * 字节消息（binaryType = arraybuffer）过来后会走这个方法
     */
    @Override
    public Object onBytes(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        return null;
    }

    /**
     * 当客户端发close flag时，会走这个方法
     */
    @Override
    public Object onClose(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        Tio.remove(channelContext, "receive close flag");
        return null;
    }

    /**
     * 字符消息（binaryType = blob）过来后会走这个方法
     *
     * @param wsRequest      wsRequest
     * @param text           text
     * @param channelContext channelContext
     * @return obj
     */
    @Override
    public Object onText(WsRequest wsRequest, String text, ChannelContext channelContext) {
        if (Objects.equals("心跳内容", text)) {
            return null;
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            SendInfo sendInfo = objectMapper.readValue(text, SendInfo.class);
            //心跳检测包
            if (Const.MSG_PING.equals(sendInfo.getCode())) {
                WsResponse wsResponse = WsResponse.fromText(text, TioServerConfig.CHARSET);
                Tio.send(channelContext, wsResponse);
            }
            //真正的消息
            else if (Const.MSG_MESSAGE.equals(sendInfo.getCode())) {
                Message message = sendInfo.getMessage();
                message.setMine(false);
                WsResponse wsResponse = WsResponse.fromText(objectMapper.writeValueAsString(sendInfo), TioServerConfig.CHARSET);
                SetWithLock<ChannelContext> channelContextSetWithLock = Tio.getByUserid(channelContext.tioConfig, message.getChatId());
                //用户没有登录，存储到离线文件
                if (channelContextSetWithLock == null || channelContextSetWithLock.size() == 0) {
                    saveMessage(message, Const.ReadStatus.READ_UNREAD);
                } else {
                    Tio.sendToUser(channelContext.tioConfig, message.getChatId(), wsResponse);
                    //入库操作
                    saveMessage(message, Const.ReadStatus.READ_READ);
                }
            }
            //准备就绪，需要发送离线消息
            else if (Const.MSG_READY.equals(sendInfo.getCode())) {
                //未读消息
                sendOffLineMessage(channelContext, objectMapper);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //返回值是要发送给客户端的内容，一般都是返回null
        return null;
    }

    /**
     * 未读消息
     *
     * @param channelContext channelContext
     * @param objectMapper   objectMapper
     * @throws IOException 抛出异常
     */
    private void sendOffLineMessage(ChannelContext channelContext, ObjectMapper objectMapper) throws IOException {
        List<ImMessage> imMessageList = iImMessageService.getUnReadMessage(channelContext.userid);
        for (ImMessage imMessage : imMessageList) {
            Message message = new Message();
            message.setChatId(imMessage.getToId());
            message.setMine(false);
            message.setType(imMessage.getType());
            ImUser imUser = imUserService.getById(imMessage.getFromId());
            message.setUsername(imUser.getName());
            message.setCid(String.valueOf(imMessage.getId()));
            message.setContent(imMessage.getContent());
            message.setTimestamp(System.currentTimeMillis());
            message.setFromId(imMessage.getFromId());
            message.setAvatar(imUser.getAvatar());
            SendInfo sendInfo1 = new SendInfo();
            sendInfo1.setCode(Const.MSG_MESSAGE);
            sendInfo1.setMessage(message);
            WsResponse wsResponse = WsResponse.fromText(objectMapper.writeValueAsString(sendInfo1), TioServerConfig.CHARSET);
            Tio.sendToUser(channelContext.tioConfig, message.getChatId(), wsResponse);
        }
    }

    /**
     * 保存信息
     *
     * @param message    信息
     * @param readStatus 是否已读
     */
    private void saveMessage(Message message, Const.ReadStatus readStatus) {
        ImMessage imMessage = new ImMessage();
        imMessage.setToId(message.getChatId());
        imMessage.setFromId(message.getFromId());
        imMessage.setSendTime(new Date());
        imMessage.setContent(message.getContent());
        imMessage.setReadStatus(readStatus);
        imMessage.setType(message.getType());
        iImMessageService.saveMessage(imMessage);
    }

}
