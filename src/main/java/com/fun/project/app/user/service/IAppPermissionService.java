package com.fun.project.app.user.service;

import com.fun.framework.web.entity.Ztree;
import com.fun.project.app.user.entity.AppPermission;

import java.util.List;

/**
 * 权限Service接口
 *
 * @author DJun
 * @date 2019-12-02
 */
public interface IAppPermissionService {
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
     * 批量删除权限
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteAppPermissionByIds(String ids);

    /**
     * 删除权限信息
     *
     * @param permId 权限ID
     * @return 结果
     */
    int deleteAppPermissionById(Long permId);

    /**
     * 校验权限字符串是否唯一
     *
     * @param permission perm
     * @return 0/1
     */
    String checkPermUnique(AppPermission permission);

    /**
     * 权限树
     * @param permission AppPermission
     * @return Ztree
     */
    List<Ztree> rolePermTreeData(AppPermission permission);

    /**
     * 查询所有权限
     *
     * @return 菜单列表
     */
    List<Ztree> permTreeData();
}