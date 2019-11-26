package com.fun.project.app.user.service;

import com.alibaba.fastjson.JSONObject;
import com.fun.project.app.user.entity.AppUser;

import java.util.List;

/**
 * @author DJun
 * @date 2019/9/12 18:16
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
    List<AppUser> selectUserList(AppUser appUser);

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
     * 通过账号更新用户信息
     *
     * @param appUser AppUser
     * @return 结果
     */
    int updateAppUserByLoginName(AppUser appUser);

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

    /**
     * 登录接口
     *
     * @param loginName 账号
     * @param pwd       密码
     * @return AppUser
     */
    JSONObject login(String loginName, String pwd);

    /**
     * 校验账号唯一
     * @param loginName loginName
     * @return 0/1
     */
    String checkLoginNameUnique(String loginName);

    /**
     * 校验uAccount唯一
     * @param uAccount UAccount
     * @return 0/1
     */
    String checkUAccountUnique(String uAccount);

    /**
     * 校验 telephone 唯一
     * @param telephone telephone
     * @return userId,email
     */
    String checkPhoneUnique(String telephone);

    /**
     * 校验 email 唯一
     * @param email 邮箱
     * @return userId,email
     */
    String checkEmailUnique(String email);
}
