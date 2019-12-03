package com.fun.project.app.user.mapper;

import com.fun.project.app.user.entity.AppPermission;

import java.util.List;

/**
 * 权限
 *
 * @author DJun
 * @date 2019-12-02
 */
public interface AppPermissionMapper {
    /**
     * 查询权限
     *
     * @param permId 权限ID
     * @return 权限
     */
    AppPermission selectAppPermissionById(Long permId);

    /**
     * 查询权限列表
     *
     * @param appPermission 权限
     * @return 权限集合
     */
    List<AppPermission> selectAppPermissionList(AppPermission appPermission);

    /**
     * 新增权限
     *
     * @param appPermission 权限
     * @return 结果
     */
    int insertAppPermission(AppPermission appPermission);

    /**
     * 修改权限
     *
     * @param appPermission 权限
     * @return 结果
     */
    int updateAppPermission(AppPermission appPermission);

    /**
     * 删除权限
     *
     * @param permId 权限ID
     * @return 结果
     */
    int deleteAppPermissionById(Long permId);

    /**
     * 批量删除权限
     *
     * @param permIds 需要删除的数据ID
     * @return 结果
     */
    int deleteAppPermissionByIds(String[] permIds);

    /**
     * 校验权限字符串唯一
     *
     * @param perm perm
     * @return AppPermission
     */
    AppPermission checkPermUnique(String perm);

    /**
     * 根据角色ID查询权限
     *
     * @param roleId 角色ID
     * @return 权限列表
     */
    List<String> selectAppPermsTree(Long roleId);
}