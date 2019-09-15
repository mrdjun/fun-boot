package com.fun.project.app.user.service;

import com.fun.project.app.user.entity.AppUser;

import java.util.List;

/**
 * created by DJun on 2019/9/12 18:16
 * desc:
 */
public interface IAppUserService {
    /**
     * 查询用户
     *
     * @param userId 用户ID
     * @return 用户
     */
    AppUser selectUserById(Long userId);

    /**
     * 查询用户列表
     *
     * @param appUser 用户
     * @return 用户集合
     */
    List<AppUser> selectUserList(AppUser appUser,int pageNum,int pageSize);

    /**
     * 新增用户
     *
     * @param appUser 用户
     * @return 结果
     */
    int insertUser(AppUser appUser);

    /**
     * 修改用户
     *
     * @param appUser 用户
     * @return 结果
     */
    int updateAppUser(AppUser appUser);

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

    AppUser login(String loginName, String pwd);

}
