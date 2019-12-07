package com.fun.project.app.user.mapper;

import com.fun.project.app.user.entity.AppUser;

import java.util.List;

/**
 * @author DJun
 * @date
 */
public interface AppUserMapper {
    /**
     * 查询用户信息
     *
     * @param userId 用户信息ID
     * @return 用户信息
     */
    AppUser selectUserById(Long userId);

    /**
     * 查询用户信息
     *
     * @param loginName 登录账号
     * @return 用户信息
     */
    AppUser selectAppUserByLoginName(String loginName);

    /**
     * 查询用户信息列表
     *
     * @param appUser 用户信息
     * @return 用户信息集合
     */
    List<AppUser> selectUserList(AppUser appUser);

    /**
     * 新增用户信息
     *
     * @param appUser 用户信息
     * @return 结果
     */
    int insertUser(AppUser appUser);

    /**
     * 修改用户信息
     *
     * @param appUser 用户信息
     * @return 结果
     */
    int updateAppUser(AppUser appUser);

    /**
     * 修改用户信息
     *
     * @param appUser 用户信息
     * @return 结果
     */
    int updateAppUserByLoginName(AppUser appUser);

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
    int deleteUserByIds(String[] userIds);

    /**
     * 登录接口
     *
     * @param loginName 账号
     * @param password  密码
     * @return AppUser
     */
    AppUser login(String loginName, String password);

    /**
     * 通过 UserId 查询用户权限信息
     *
     * @param userId userId
     * @return List<AppUserPerms>
     */
    List<String> selectUserPermsByUserId(Long userId);

    /**
     * 校验账号唯一
     *
     * @param loginName loginName
     * @return 0/1
     */
    AppUser checkLoginNameUnique(String loginName);

    /**
     * 校验uAccount唯一
     *
     * @param uAccount UAccount
     * @return 0/1
     */
    AppUser checkUAccountUnique(String uAccount);

    /**
     * 校验 telephone 唯一
     *
     * @param telephone telephone
     * @return userId, email
     */
    AppUser checkPhoneUnique(String telephone);

    /**
     * 校验 email 唯一
     *
     * @param email 邮箱
     * @return userId, email
     */
    AppUser checkEmailUnique(String email);

    /**
     * 根据条件分页查询已分配用户角色列表
     *
     * @param appUser 用户信息
     * @return 用户信息集合信息
     */
    List<AppUser> selectAllocatedList(AppUser appUser);

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param appUser 用户信息
     * @return 用户信息集合信息
     */
    List<AppUser> selectUnallocatedList(AppUser appUser);
}
