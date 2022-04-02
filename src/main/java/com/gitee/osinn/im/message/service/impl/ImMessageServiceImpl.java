package com.gitee.osinn.im.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gitee.osinn.im.constants.Const;
import com.gitee.osinn.im.message.entity.ImMessage;
import com.gitee.osinn.im.message.mapper.ImMessageMapper;
import com.gitee.osinn.im.message.service.IImMessageService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 服务实现类
 *
 * @author 乐天
 * @since 2018-10-08
 */
@Service
@Qualifier(value = "iImMessageService")
public class ImMessageServiceImpl extends ServiceImpl<ImMessageMapper, ImMessage> implements IImMessageService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveMessage(ImMessage imMessage) {
        new SaveChatMessageThread(imMessage).run();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<ImMessage> getUnReadMessage(String toId) {
        QueryWrapper<ImMessage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("to_id", toId);
        queryWrapper.eq("read_status", "1");
        List<ImMessage> messageList = baseMapper.selectList(queryWrapper);
        for (ImMessage message : messageList) {
            message.setReadStatus(Const.ReadStatus.READ_READ);
            this.updateById(message);
        }
        return messageList;
    }

    /**
     * 内部类
     */
    class SaveChatMessageThread implements Runnable {

        private final ImMessage imMessage;

        public SaveChatMessageThread(ImMessage imMessage) {
            this.imMessage = imMessage;
        }

        @Override
        public void run() {
            save(imMessage);
        }
    }
}
