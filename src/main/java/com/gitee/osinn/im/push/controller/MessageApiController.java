package com.gitee.osinn.im.push.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.gitee.osinn.im.constants.Const;
import com.gitee.osinn.im.message.entity.ImMessage;
import com.gitee.osinn.im.message.service.IImMessageService;
import com.gitee.osinn.im.push.entity.Message;
import com.gitee.osinn.im.push.entity.SendInfo;
import com.gitee.osinn.im.sys.ImUser;
import com.gitee.osinn.im.sys.RedisService;
import com.gitee.osinn.im.tio.StartTioRunner;
import com.gitee.osinn.im.tio.TioServerConfig;
import com.gitee.osinn.im.tio.TioWsMsgHandler;
import com.gitee.osinn.im.tio.WsOnlineContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.server.ServerTioConfig;
import org.tio.websocket.common.WsResponse;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 前端控制器
 *
 * @author 乐天
 * @since 2018-10-07
 */
@RestController
@RequestMapping("/api/message")
public class MessageApiController {

    private final Logger logger = LoggerFactory.getLogger(MessageApiController.class);

    @Resource
    private StartTioRunner startTioRunner;

    @Resource
    @Qualifier(value = "iImMessageService")
    private IImMessageService iImMessageService;

    @Autowired
    private RedisService redisService;

    /**
     * 发送信息给用户
     * 注意：目前仅支持发送给在线用户
     *
     * @param token 接收方
     * @param msg    消息内容
     */
    @PostMapping("sendMsg")
    public void sendMsg(String token, String msg, HttpServletRequest request) throws Exception {
        String host = Const.getHost(request);
        ServerTioConfig serverTioConfig = startTioRunner.getAppStarter().getWsServerStarter().getServerTioConfig();

        SendInfo sendInfo = new SendInfo();
        sendInfo.setCode("2");
        Message message = new Message();
        message.setChatId("system");
        message.setFromId("system");
        message.setContent(msg);
        message.setMine(false);
        message.setTimestamp(System.currentTimeMillis());
        message.setType("0");
        message.setAvatar(host + "/img/icon.png");
        message.setUsername("系统消息");
        sendInfo.setMessage(message);
        ImUser imUser = (ImUser) redisService.get(token);
        String userId = TioWsMsgHandler.TO_ID + imUser.getId();

        ChannelContext cc = WsOnlineContext.getChannelContextByUser(userId);
        if (cc != null && !cc.isClosed) {
            WsResponse wsResponse = WsResponse.fromText(new ObjectMapper().writeValueAsString(sendInfo), TioServerConfig.CHARSET);
            Tio.sendToUser(serverTioConfig, userId, wsResponse);
        } else {
            saveMessage(message, Const.ReadStatus.READ_UNREAD, userId);
        }
    }


    private void saveMessage(Message message, Const.ReadStatus readStatus, String userId) {
        ImMessage imMessage = new ImMessage();
        imMessage.setToId(userId);
        imMessage.setFromId(message.getFromId());
        imMessage.setSendTime(new Date());
        imMessage.setContent(message.getContent());
        imMessage.setReadStatus(readStatus);
        imMessage.setType(message.getType());
        iImMessageService.saveMessage(imMessage);
    }
}
