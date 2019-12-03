package com.fun.project.app.user.mapper;

import com.fun.project.app.user.entity.AppUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * app用户角色
 *
 * @author DJun
 * @date 2019-11-28
 */
public interface AppUserRoleMapper {
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
     * 删除app用户角色
     *
     * @param userId app用户角色ID
     * @return 结果
     */
    int deleteAppUserRoleById(Long userId);

    /**
     * 批量删除app用户角色
     *
     * @param userIds 需要删除的数据ID
     * @return 结果
     */
    int deleteAppUserRoleByIds(String[] userIds);

    /**
     * 批量新增用户角色信息
     *
     * @param userRoleList 用户角色列表
     * @return 结果
     */
    int batchUserRole(List<AppUserRole> userRoleList);

    /**
     * 批量取消授权用户角色
     *
     * @param roleId  角色ID
     * @param userIds 需要删除的用户数据ID
     * @return 结果
     */
    int deleteUserRoleInfos(@Param("roleId") Long roleId, @Param("userIds") Long[] userIds);

    /**
     * 取消单个用户授权
     *
     * @param roleId roleId
     * @param userId userId
     * @return 行数
     */
    int deleteAuthUser(Long roleId, Long userId);
}