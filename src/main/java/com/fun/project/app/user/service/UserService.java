package com.fun.project.app.user.service;

import com.fun.project.app.user.entity.User;

import java.util.List;

/**
 * created by DJun on 2019/9/12 18:16
 * desc:
 */
public interface UserService {
    /**
     * 查询用户
     *
     * @param userId 用户ID
     * @return 用户
     */
    User selectUserById(Long userId);

    /**
     * 查询用户列表
     *
     * @param user 用户
     * @return 用户集合
     */
    List<User> selectUserList(User user);

    /**
     * 新增用户
     *
     * @param user 用户
     * @return 结果
     */
    int insertUser(User user);

    /**
     * 修改用户
     *
     * @param user 用户
     * @return 结果
     */
    int updateUser(User user);

    /**
     * 批量删除用户
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteUserByIds(String ids);

    /**
     * 删除用户信息
     *
     * @param userId 用户ID
     * @return 结果
     */
    int deleteUserById(Long userId);

    User login(String loginName, String pwd);

}
