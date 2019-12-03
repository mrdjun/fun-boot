package com.fun.project.app.user.service;

import com.fun.project.app.user.entity.AppRolePerm;
import java.util.List;

/**
 * 角色权限Service接口
 *
 * @author DJun
 * @date 2019-12-02
 */
public interface IAppRolePermService {
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
     * 批量删除角色权限
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteAppRolePermByIds(String ids);

    /**
     * 删除角色权限信息
     *
     * @param roleId 角色权限ID
     * @return 结果
     */
    int deleteAppRolePermById(Long roleId);
}