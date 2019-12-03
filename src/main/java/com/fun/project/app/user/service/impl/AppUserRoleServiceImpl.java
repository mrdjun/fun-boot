package com.fun.project.app.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.fun.project.app.user.entity.AppUser;
import com.fun.project.app.user.mapper.AppUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fun.project.app.user.mapper.AppUserRoleMapper;
import com.fun.project.app.user.entity.AppUserRole;
import com.fun.project.app.user.service.IAppUserRoleService;
import com.fun.common.utils.text.Convert;

/**
 * app用户角色
 *
 * @author DJun
 * @date 2019-11-28
 */
@Service
public class AppUserRoleServiceImpl implements IAppUserRoleService {
    @Autowired
    private AppUserRoleMapper appUserRoleMapper;
    @Autowired
    private AppUserMapper appUserMapper;
    /**
     * 查询app用户角色
     *
     * @param userId app用户角色ID
     * @return app用户角色
     */
    @Override
    public AppUserRole selectAppUserRoleById(Long userId) {
        return appUserRoleMapper.selectAppUserRoleById(userId);
    }

    /**
     * 查询app用户角色列表
     *
     * @param appUserRole app用户角色
     * @return app用户角色
     */
    @Override
    public List<AppUserRole> selectAppUserRoleList(AppUserRole appUserRole) {
        return appUserRoleMapper.selectAppUserRoleList(appUserRole);
    }

    /**
     * 新增app用户角色
     *
     * @param appUserRole app用户角色
     * @return 结果
     */
    @Override
    public int insertAppUserRole(AppUserRole appUserRole) {
        return appUserRoleMapper.insertAppUserRole(appUserRole);
    }

    /**
     * 修改app用户角色
     *
     * @param appUserRole app用户角色
     * @return 结果
     */
    @Override
    public int updateAppUserRole(AppUserRole appUserRole) {
        return appUserRoleMapper.updateAppUserRole(appUserRole);
    }

    /**
     * 删除app用户角色对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAppUserRoleByIds(String ids) {
        return appUserRoleMapper.deleteAppUserRoleByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除app用户角色信息
     *
     * @param userId app用户角色ID
     * @return 结果
     */
    @Override
    public int deleteAppUserRoleById(Long userId) {
        return appUserRoleMapper.deleteAppUserRoleById(userId);
    }

    @Override
    public List<AppUser> selectAllocatedList(AppUser user) {
        return appUserMapper.selectAllocatedList(user);
    }

    @Override
    public List<AppUser> selectUnallocatedList(AppUser user) {
        return appUserMapper.selectUnallocatedList(user);
    }


    @Override
    public int insertAuthUsers(Long roleId, String userIds) {
        Long[] users = Convert.toLongArray(userIds);
        // 新增用户与角色关系
        List<AppUserRole> list = new ArrayList<>();
        for (Long userId : users) {
            AppUserRole ur = new AppUserRole();
            ur.setUserId(userId);
            ur.setRoleId(roleId);
            list.add(ur);
        }
        return appUserRoleMapper.batchUserRole(list);
    }

    @Override
    public int deleteAuthUsers(Long roleId, String userIds) {
        return appUserRoleMapper.deleteUserRoleInfos(roleId,Convert.toLongArray(userIds));
    }

    @Override
    public int deleteAuthUser(Long roleId, Long userId) {
        return appUserRoleMapper.deleteAuthUser(roleId,userId);
    }
}