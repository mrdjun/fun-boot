package com.fun.project.app.user.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fun.project.app.user.mapper.AppRolePermMapper;
import com.fun.project.app.user.entity.AppRolePerm;
import com.fun.project.app.user.service.IAppRolePermService;
import com.fun.common.utils.text.Convert;

/**
 * 角色权限
 *
 * @author DJun
 * @date 2019-12-02
 */
@Service
public class AppRolePermServiceImpl implements IAppRolePermService {
    @Autowired
    private AppRolePermMapper appRolePermMapper;

    /**
     * 查询角色权限
     *
     * @param roleId 角色权限ID
     * @return 角色权限
     */
    @Override
    public AppRolePerm selectAppRolePermById(Long roleId) {
        return appRolePermMapper.selectAppRolePermById(roleId);
    }

    /**
     * 查询角色权限列表
     *
     * @param appRolePerm 角色权限
     * @return 角色权限
     */
    @Override
    public List<AppRolePerm> selectAppRolePermList(AppRolePerm appRolePerm) {
        return appRolePermMapper.selectAppRolePermList(appRolePerm);
    }

    /**
     * 新增角色权限
     *
     * @param appRolePerm 角色权限
     * @return 结果
     */
    @Override
    public int insertAppRolePerm(AppRolePerm appRolePerm) {
        return appRolePermMapper.insertAppRolePerm(appRolePerm);
    }

    /**
     * 修改角色权限
     *
     * @param appRolePerm 角色权限
     * @return 结果
     */
    @Override
    public int updateAppRolePerm(AppRolePerm appRolePerm) {
        return appRolePermMapper.updateAppRolePerm(appRolePerm);
    }

    /**
     * 删除角色权限对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAppRolePermByIds(String ids) {
        return appRolePermMapper.deleteAppRolePermByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除角色权限信息
     *
     * @param roleId 角色权限ID
     * @return 结果
     */
    @Override
    public int deleteAppRolePermById(Long roleId) {
        return appRolePermMapper.deleteAppRolePermById(roleId);
    }
}