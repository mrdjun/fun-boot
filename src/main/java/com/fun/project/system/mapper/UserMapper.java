package com.fun.project.system.mapper;

import com.fun.project.system.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户信息Mapper接口
 */
public interface UserMapper {
    /**
     * 查询用户信息
     *
     * @param userId 用户信息ID
     * @return 用户信息
     */
    User selectUserById(@Param("userId") Long userId);

    /**
     * 查询用户信息列表
     *
     * @param User 用户信息
     * @return 用户信息集合
     */
    List<User> selectUserList(User User);

    /**
     * 新增用户信息
     *
     * @param User 用户信息
     * @return 结果
     */
    int insertUser(User User);

    /**
     * 修改用户信息
     *
     * @param User 用户信息
     * @return 结果
     */
    int updateUser(User User);

    /**
     * 删除用户信息
     *
     * @param userId 用户信息ID
     * @return 结果
     */
    int deleteUserById(Long userId);

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的数据ID
     * @return 结果
     */
    int deleteUserByIds(String userIds);

    /** 登录接口 */
    User login(@Param("loginName") String loginName, @Param("password") String password);
}