package com.fun.project.app.user.mapper;

import com.fun.project.app.user.entity.AppRolePerm;

import java.util.List;

/**
 * 角色权限
 *
 * @author DJun
 * @date 2019-12-02
 */
public interface AppRolePermMapper {
    /**
     * 查询角色权限
     *
     * @param roleId 角色权限ID
     * @return 角色权限
     */
    AppRolePerm selectAppRolePermById(Long roleId);

    /**
     * 查询角色权限列表
     *
     * @param appRolePerm 角色权限
     * @return 角色权限集合
     */
    List<AppRolePerm> selectAppRolePermList(AppRolePerm appRolePerm);

    /**
     * 新增角色权限
     *
     * @param appRolePerm 角色权限
     * @return 结果
     */
    int insertAppRolePerm(AppRolePerm appRolePerm);

    /**
     * 修改角色权限
     *
     * @param appRolePerm 角色权限
     * @return 结果
     */
    int updateAppRolePerm(AppRolePerm appRolePerm);

    /**
     * 删除角色权限
     *
     * @param roleId 角色权限ID
     * @return 结果
     */
    int deleteAppRolePermById(Long roleId);

    /**
     * 批量删除角色权限
     *
     * @param roleIds 需要删除的数据ID
     * @return 结果
     */
    int deleteAppRolePermByIds(String[] roleIds);

    /**
     * 解除权限与角色的关联
     *
     * @param permId permId
     * @param roleId roleId
     * @return 结果
     */
    int deleteWithRolePerm(Long roleId, Long permId);


    /**
     * 通过permId查询roleId
     *
     * @param permId permId
     * @return roleId[]
     */
    long[] selectRoleIdByPermId(Long permId);

    /**
     * 批量新增角色的权限
     *
     * @param rolePermList rolePermList
     * @return int
     */
    int batchAppRolePerm(List<AppRolePerm> rolePermList);
}