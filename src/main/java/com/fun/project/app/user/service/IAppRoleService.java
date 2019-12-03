package com.fun.project.app.user.service;


import com.fun.framework.web.service.IAuthUserService;
import com.fun.project.app.user.entity.AppRole;
import com.fun.project.app.user.entity.AppUser;

import java.util.List;

/**
 * 角色Service接口
 *
 * @author DJun
 * @date 2019-11-28
 */
public interface IAppRoleService {
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
     * 批量删除角色
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteAppRoleByIds(String ids);

    /**
     * 删除角色信息
     *
     * @param roleId 角色ID
     * @return 结果
     */
    int deleteAppRoleById(Long roleId);

    /**
     * 查询所有角色
     *
     * @return 角色列表
     */
    List<AppRole> selectRoleAll();

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<AppRole> selectAppRolesByUserId(Long userId);

    /**
     * 校验权限字符串唯一
     *
     * @param role 角色
     * @return 0/1
     */
    String checkRoleKeyUnique(AppRole role);

    /**
     * 修改 ums_role
     *
     * @param role AppRole
     * @return 0/1
     */
    int updateAppRoleByRoleId(AppRole role);
}
