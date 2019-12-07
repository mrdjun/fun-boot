package com.fun.project.admin.system.service;

import com.fun.project.admin.system.entity.user.AdminUser;

import java.util.List;


/**
 * 管理员用户
 *
 * @author DJun
 * @date 2019/9/14 9:43
 */
public interface IAdminUserService {

    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    List<AdminUser> selectAdminUserList(AdminUser user);

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
    AdminUser selectAdminUserById(Long userId);

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    int deleteAdminUserById(Long userId);

    /**
     * 批量删除用户信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     * @throws Exception 异常
     */
    int deleteAdminUserByIds(String ids) throws Exception;

    /**
     * 新增用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    int insertAdminUser(AdminUser user);

    /**
     * 保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    int updateAdminUser(AdminUser user);

    /**
     * 修改管理员信息
     *
     * @param user 用户信息
     * @return 修改行数
     */
    int updateUserInfo(AdminUser user);
    /**
     * 通过 loginName 修改用户表
     * @param adminUser userInfo
     * @return num
     */
    int updateUserInfoByLoginName(AdminUser adminUser);

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
    String checkLoginNameUnique(String loginName);

    /**
     * 校验手机号码是否唯一
     *
     * @param user telephone
     * @return 结果
     */
    String checkPhoneUnique(AdminUser user);

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    String checkEmailUnique(AdminUser user);

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

    /**
     * 根据条件分页查询已分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    List<AdminUser> selectAllocatedList(AdminUser user);

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    List<AdminUser> selectUnallocatedList(AdminUser user);
    /**
     * 根据用户ID查询用户所属岗位组
     *
     * @param userId 用户ID
     * @return 结果
     */
     String selectUserPostGroup(Long userId);

    /**
     * 导入用户数据
     *
     * @param userList 用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
     String importUser(List<AdminUser> userList, Boolean isUpdateSupport);
}
