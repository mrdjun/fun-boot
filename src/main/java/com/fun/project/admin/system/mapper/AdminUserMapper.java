package com.fun.project.admin.system.mapper;

import com.fun.project.admin.system.entity.user.AdminUser;

import java.util.List;

/**
 * @author DJun
 * @date 2019/9/14 9:42
 */
public interface AdminUserMapper {
    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    List<AdminUser> selectAdminUserList(AdminUser user);

    /**
     * 根据条件分页查询未已配用户角色列表
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
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    AdminUser selectUserByLoginName(String userName);

    /**
     * 通过手机号码查询用户
     *
     * @param telephone 手机号码
     * @return 用户对象信息
     */
    AdminUser selectAdminUserByTelephone(String telephone);

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
     */
    int deleteUserByIds(Long[] ids);

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    int updateUser(AdminUser user);

    /**
     * 根据loginName修改用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    int updateUserInfoByLoginName(AdminUser user);

    /**
     * 新增用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    int insertUser(AdminUser user);

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
     * @param telephone 手机号码
     * @return 结果
     */
    AdminUser checkPhoneUnique(String telephone);

    /**
     * 校验email是否唯一
     *
     * @param email 用户邮箱
     * @return 结果
     */
    AdminUser checkEmailUnique(String email);

}
