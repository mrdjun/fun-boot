package com.fun.project.app.user.service;

import com.fun.framework.web.service.IAuthUserService;
import com.fun.project.app.user.entity.AppUser;
import com.fun.project.app.user.entity.AppUserRole;
import java.util.List;

/**
 * app用户角色Service接口
 *
 * @author DJun
 * @date 2019-11-28
 */
public interface IAppUserRoleService extends IAuthUserService<AppUser> {
    /**
     * 查询app用户角色
     *
     * @param userId app用户角色ID
     * @return app用户角色
     */
    AppUserRole selectAppUserRoleById(Long userId);

    /**
     * 查询app用户角色列表
     *
     * @param appUserRole app用户角色
     * @return app用户角色集合
     */
    List<AppUserRole> selectAppUserRoleList(AppUserRole appUserRole);

    /**
     * 新增app用户角色
     *
     * @param appUserRole app用户角色
     * @return 结果
     */
    int insertAppUserRole(AppUserRole appUserRole);

    /**
     * 修改app用户角色
     *
     * @param appUserRole app用户角色
     * @return 结果
     */
    int updateAppUserRole(AppUserRole appUserRole);

    /**
     * 批量删除app用户角色
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteAppUserRoleByIds(String ids);

    /**
     * 删除app用户角色信息
     *
     * @param userId app用户角色ID
     * @return 结果
     */
    int deleteAppUserRoleById(Long userId);

}