package com.gitee.osinn.im.sys;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 用户service
 *
 * @author 乐天-im
 * @since 2018-10-07
 */
public interface IImUserService extends IService<ImUser> {

    /**
     * 获取单个user
     *
     * @param id id
     * @return 字符串
     */
    ImUser getById(Long id);

    /**
     * 根据登录名称获取用户
     *
     * @param loginName 登录名
     * @return 用户
     */
    ImUser getByLoginName(String loginName);

//
//    /**
//     * 根据用户id 获取用户所有的群
//     *
//     * @param userId 用户
//     * @return 群List
//     */
//    List<ImChatGroup> getChatGroups(String userId);
//
//    /**
//     * 获取群组的用户
//     *
//     * @param chatId 群组id
//     * @return 用户List
//     */
//    List<ImUser> getChatUserList(String chatId);


    /**
     * 注册用户
     *
     * @param imUser 用户对象
     */
    void registerUser(ImUser imUser);


    Map<String, Long> groupByDept();
}
