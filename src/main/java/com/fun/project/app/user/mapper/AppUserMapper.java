package com.fun.project.app.user.mapper;

import com.fun.project.app.user.entity.AppUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * created by DJun on 2019/9/12 18:15
 * desc:
 */
public interface AppUserMapper {
    /**
     * 查询用户信息
     *
     * @param userId 用户信息ID
     * @return 用户信息
     */
    AppUser selectUserById(@Param("userId") Long userId);

    /**
     * 查询用户信息列表
     *
     * @param AppUser 用户信息
     * @return 用户信息集合
     */
    List<AppUser> selectUserList(AppUser AppUser);

    /**
     * 新增用户信息
     *
     * @param AppUser 用户信息
     * @return 结果
     */
    int insertUser(AppUser AppUser);

    /**
     * 修改用户信息
     *
     * @param AppUser 用户信息
     * @return 结果
     */
    int updateAppUser(AppUser AppUser);

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
    AppUser login(@Param("loginName") String loginName, @Param("password") String password);
}
