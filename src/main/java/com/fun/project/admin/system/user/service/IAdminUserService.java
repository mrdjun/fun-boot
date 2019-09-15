package com.fun.project.admin.system.user.service;

import com.fun.project.admin.system.user.entity.AdminUser;

import java.util.List;

/**
 * created by DJun on 2019/9/14 9:43
 * desc:
 */
public interface IAdminUserService {


    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
     List<AdminUser> selectUserList(AdminUser user,int pageNum,int pageSize);

    /**
     * 通过用户名查询用户
     *
     * @param loginName 用户名
     * @return 用户对象信息
     */
     AdminUser selectUserByLoginName(String loginName);

    /**
     * 通过手机号码查询用户
     *
     * @param telephone 手机号码
     * @return 用户对象信息
     */
     AdminUser selectUserByTelephone(String telephone);

    /**
     * 通过邮箱查询用户
     *
     * @param email 邮箱
     * @return 用户对象信息
     */
     AdminUser selectUserByEmail(String email);

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
     AdminUser selectUserById(Long userId);

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
     int deleteUserById(Long userId);

    /**
     * 批量删除用户信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     * @throws Exception 异常
     */
     int deleteUserByIds(String ids) throws Exception;

    /**
     * 保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
     int insertUser(AdminUser user);

    /**
     * 保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
     int updateUser(AdminUser user);

    /**
     * 修改用户详细信息
     *
     * @param user 用户信息
     * @return 结果
     */
     int updateUserInfo(AdminUser user);

    /**
     * 修改用户密码信息
     *
     * @param user 用户信息
     * @return 结果
     */
     int resetUserPwd(AdminUser user);

    /**
     * 校验用户名称是否唯一
     *
     * @param loginName 登录名称
     * @return 结果
     */
    int checkLoginNameUnique(String loginName);

    /**
     * 校验手机号码是否唯一
     *
     * @param telephone telephone
     * @return 结果
     */
    int checkPhoneUnique(String telephone);

    /**
     * 校验email是否唯一
     *
     * @param email 用户信息
     * @return 结果
     */
    int checkEmailUnique(String email);

    /**
     * 根据用户ID查询用户所属角色组
     *
     * @param userId 用户ID
     * @return 结果
     */
     String selectUserRoleGroup(Long userId);

    /**
     * 用户状态修改
     *
     * @param user 用户信息
     * @return 结果
     */
     int changeStatus(AdminUser user);

}
