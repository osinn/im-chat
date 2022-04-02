package com.gitee.osinn.im.constants;

import lombok.Getter;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述
 *
 * @author wency_cai
 */
public class Const {

    /**
     * 消息
     */
    public static final String MSG_MESSAGE = "2";

    /**
     * 心跳
     */
    public static final String MSG_PING = "0";

    /**
     * 链接就绪
     */
    public static final String MSG_READY = "1";


    public static final String TOKEN_CACHE_PREFIX = "user_id_";

    public static String getHost(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        return url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();
    }

    @Getter
    public enum ReadStatus {

        READ_READ("已读"),
        READ_UNREAD("未读");

        private final String text;

        ReadStatus(String text) {
            this.text = text;
        }
    }
}
