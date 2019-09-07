package com.fun.project.system.service;

import com.fun.project.system.entity.User;

import java.util.List;

/**
 * 用户Service接口
 *
 * @author cqjava
 * @date 2019-09-07
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
}