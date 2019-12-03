package com.fun.project.app.user.mapper;


import com.fun.project.app.user.entity.AppRole;
import com.fun.project.app.user.entity.AppUser;

import java.util.List;

/**
 * 角色
 *
 * @author DJun
 * @date 2019-11-28
 */
public interface AppRoleMapper {
    /**
     * 查询角色
     *
     * @param roleId 角色ID
     * @return 角色
     */
    AppRole selectAppRoleById(Long roleId);

    /**
     * 查询角色列表
     *
     * @param appRole 角色
     * @return 角色集合
     */
    List<AppRole> selectAppRoleList(AppRole appRole);

    /**
     * 新增角色
     *
     * @param appRole 角色
     * @return 结果
     */
    int insertAppRole(AppRole appRole);

    /**
     * 修改角色
     *
     * @param appRole 角色
     * @return 结果
     */
    int updateAppRole(AppRole appRole);

    /**
     * 删除角色
     *
     * @param roleId 角色ID
     * @return 结果
     */
    int deleteAppRoleById(Long roleId);

    /**
     * 批量删除角色
     *
     * @param roleIds 需要删除的数据ID
     * @return 结果
     */
    int deleteAppRoleByIds(String[] roleIds);

    /**
     * 查询用户的角色
     * @param userId userId
     * @return 角色列表
     */
    List<AppRole> selectRoleByUserId(Long userId);

    /**
     * 校验角色字符串唯一
     * @param roleKey roleKey
     * @return AppRole
     */
    AppRole checkRoleKeyUnique(String roleKey);
}
