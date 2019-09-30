package com.fun.project.admin.system.service;

import com.fun.project.admin.system.entity.Role;
import com.fun.project.admin.system.entity.user.UserRole;

import java.util.List;
import java.util.Set;

/**
 * created by DJun on 2019/9/14 17:07
 * desc:
 */
public interface IRoleService {
    /**
     * 根据条件分页查询角色数据
     *
     * @param role 角色信息
     * @return 角色数据集合信息
     */
     List<Role> selectRoleList(Role role);

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 权限列表
     */
     Set<String> selectRoleKeys(Long userId);

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
     List<Role> selectRolesByUserId(Long userId);

    /**
     * 查询所有角色
     *
     * @return 角色列表
     */
     List<Role> selectRoleAll();

    /**
     * 通过角色ID查询角色
     *
     * @param roleId 角色ID
     * @return 角色对象信息
     */
     Role selectRoleById(Long roleId);

    /**
     * 通过角色ID删除角色
     *
     * @param roleId 角色ID
     * @return 结果
     */
     boolean deleteRoleById(Long roleId);

    /**
     * 批量删除角色用户信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     * @throws Exception 异常
     */
     int deleteRoleByIds(String ids) throws Exception;

    /**
     * 新增保存角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
     int insertRole(Role role);

    /**
     * 修改保存角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
     int updateRole(Role role);

    /**
     * 修改数据权限信息
     *
     * @param role 角色信息
     * @return 结果
     */
     int authDataScope(Role role);

    /**
     * 校验角色名称是否唯一
     *
     * @param roleName roleName
     * @return 结果
     */
     int checkRoleNameUnique(String roleName);

    /**
     * 校验角色权限是否唯一
     *
     * @param roleKey roleKey
     * @return 结果
     */
    int checkRoleKeyUnique(String roleKey);

    /**
     * 通过角色ID查询角色使用数量
     *
     * @param roleId 角色ID
     * @return 结果
     */
     int countUserRoleByRoleId(Long roleId);

    /**
     * 角色状态修改
     *
     * @param role 角色信息
     * @return 结果
     */
     int changeStatus(Role role);

    /**
     * 取消授权用户角色
     *
     * @param userRole 用户和角色关联信息
     * @return 结果
     */
     int deleteAuthUser(UserRole userRole);

    /**
     * 批量取消授权用户角色
     *
     * @param roleId 角色ID
     * @param userIds 需要删除的用户数据ID
     * @return 结果
     */
     int deleteAuthUsers(Long roleId, String userIds);

    /**
     * 批量选择授权用户角色
     *
     * @param roleId 角色ID
     * @param userIds 需要删除的用户数据ID
     * @return 结果
     */
     int insertAuthUsers(Long roleId, String userIds);
}
