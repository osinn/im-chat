package com.gitee.osinn.im.message.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gitee.osinn.im.message.entity.ImMessage;
import com.gitee.osinn.im.sys.IImUserService;
import com.gitee.osinn.im.sys.ImUser;
import com.gitee.osinn.im.message.service.IImMessageService;
import com.gitee.osinn.im.push.entity.Message;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 乐天
 * @since 2018-10-08
 */
@RestController
@RequestMapping("/api/message")
public class ImMessageController {

    public static final int PAGE_SIZE = 20;

    public static final String FRIEND = "0";

    @Resource
    @Qualifier(value = "iImMessageService")
    private IImMessageService iImMessageService;

    @Resource
    @Qualifier(value = "imUserService")
    private IImUserService imUserService;


    /**
     * 获取聊天记录
     *
     * @param chatId 如果是单聊，是用户的ID，如果是多聊，是chat id
     * @return json
     */
    @ResponseBody
    @RequestMapping("list")
    public Map<String, Object> list(String chatId, String fromId,String chatType, Long pageNo) {
        if (StringUtils.isEmpty(chatId) || StringUtils.isEmpty(fromId)) {
            return new HashMap<>();
        }
        Page<ImMessage> page = new Page<>();
        page.setSize(PAGE_SIZE);
        if (pageNo == null) {
            pageNo = 0L;
        }
        page.setCurrent(pageNo);
        QueryWrapper<ImMessage> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        if(FRIEND.equals(chatType)){
            wrapper.and(wrapper1 -> wrapper1.eq("to_id", chatId)
                    .eq("from_id", fromId));
            wrapper.or(wrapper2 -> wrapper2.eq("from_id", chatId)
                    .eq("to_id", fromId));
        }else {
            wrapper.eq("to_id",chatId);
        }
        IPage<ImMessage> messageIPage = iImMessageService.page(page, wrapper);

        List<ImMessage> imMessageList = messageIPage.getRecords();
        List<Message> messageList = new ArrayList<>();
        for (ImMessage imMessage : imMessageList) {
            Message message = new Message();
            message.setChatId(imMessage.getToId());
            message.setMine(fromId.equals(imMessage.getFromId()));
            message.setType(imMessage.getType());
            ImUser imUser = imUserService.getById(imMessage.getFromId());
            message.setAvatar(imUser.getAvatar());
            message.setUsername(imUser.getName());
            message.setFromId(imMessage.getFromId());
            message.setCid(String.valueOf(imMessage.getId()));
            message.setContent(imMessage.getContent());
            message.setTimestamp(imMessage.getSendTime().getTime());
            messageList.add(message);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("messageList", messageList);
        map.put("pageNo", pageNo);
        map.put("count", messageIPage.getTotal());
        map.put("pageSize", messageIPage.getSize());
        return map;
    }

}
