package com.gitee.osinn.im.push.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * websocket 通讯的json
 */
@Data
public class Message implements Serializable {

    /**
     * 消息来源用户名
     */
    private String username;

    /**
     * 发送者头像
     */
    private String avatar;

    /**
     * 消息的来源ID（如果是私聊，则是用户id，如果是群聊，则是群组id）
     */
    private String chatId;

    /**
     * 消息类型 单聊/群聊
     */
    private String type;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息id
     */
    private String cid;

    /**
     * 是否本人发送
     */
    private boolean mine;

    /**
     * 消息的发送者id
     */
    private String fromId;

    /**
     * 服务端时间戳毫秒数
     */
    private long timestamp;


}
