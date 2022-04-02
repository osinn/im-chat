package com.gitee.osinn.im.message.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.osinn.im.constants.Const;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ImMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 接收人
     */
    private String toId;

    /**
     * 发送人id
     */
    private String fromId;

    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * 发送内容
     */
    private String content;

    /**
     * 消息类型 单聊/群聊
     */
    private String type;

    /**
     *  已读/未读
     */
    private Const.ReadStatus readStatus;


}
