package com.gitee.osinn.im.push.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * websocket 通讯的json 封装
 */
@Data
public class SendInfo implements Serializable {



    /**
     * 发送信息的代码
     */
    private String code;

    /**
     * 信息
     */
    private Message message;


}
