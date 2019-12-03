package com.fun.project.app.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.fun.common.constant.Constants;
import com.fun.common.exception.FunBootException;
import com.fun.common.utils.StringUtils;
import com.fun.common.utils.TimestampUtil;
import com.fun.framework.shiro.helper.ShiroUtils;
import com.fun.project.app.user.entity.AppRolePerm;
import com.fun.project.app.user.mapper.AppRolePermMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fun.project.app.user.mapper.AppRoleMapper;
import com.fun.project.app.user.entity.AppRole;
import com.fun.project.app.user.service.IAppRoleService;
import com.fun.common.utils.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 角色
 *
 * @author DJun
 * @date 2019-11-28
 */
@Service
public class AppRoleServiceImpl implements IAppRoleService {
    @Autowired
    private AppRoleMapper appRoleMapper;
    @Autowired
    private AppRolePermMapper rolePermMapper;

    /**
     * 查询角色
     *
     * @param roleId 角色ID
     * @return 角色
     */
    @Override
    public AppRole selectAppRoleById(Long roleId) {
        return appRoleMapper.selectAppRoleById(roleId);
    }

    /**
     * 查询角色列表
     *
     * @param appRole 角色
     * @return 角色
     */
    @Override
    public List<AppRole> selectAppRoleList(AppRole appRole) {
        return appRoleMapper.selectAppRoleList(appRole);
    }

    /**
     * 新增角色
     *
     * @param appRole 角色
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = FunBootException.class)
    public int insertAppRole(AppRole appRole) {
        appRole.setCreateBy(ShiroUtils.getLoginName());
        appRole.setCreateTime(TimestampUtil.getCurrentTimestamp13());
        appRoleMapper.insertAppRole(appRole);
        return insertRolePerm(appRole);
    }

    /**
     * 修改角色
     *
     * @param appRole 角色
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = FunBootException.class)
    public int updateAppRole(AppRole appRole) {
        appRole.setUpdateBy(ShiroUtils.getLoginName());
        appRole.setUpdateTime(TimestampUtil.getCurrentTimestamp13());
        int rows = 1;
        // 修改角色信息
        rows = appRoleMapper.updateAppRole(appRole);
        // 修改角色与权限的关联
        if (StringUtils.isNotNull(appRole.getPermIds()) && appRole.getPermIds().length > 0) {
            rolePermMapper.deleteAppRolePermById(appRole.getRoleId());
            rows = insertRolePerm(appRole);
        }
        return rows;
    }

    private int insertRolePerm(AppRole role) {
        int rows = 1;
        // 新增用户与角色管理
        List<AppRolePerm> list = new ArrayList<>();
        for (Long permId : role.getPermIds()) {
            AppRolePerm rp = new AppRolePerm();
            rp.setPermId(permId);
            rp.setRoleId(role.getRoleId());
            list.add(rp);
        }
        if (list.size() > 0) {
            rows = rolePermMapper.batchAppRolePerm(list);
        }
        return rows;
    }

    /**
     * 删除角色对象
     *
     * @param roleIds 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAppRoleByIds(String roleIds) {
        String[] ids = Convert.toStrArray(roleIds);
        // 删除角色关联的权限
        rolePermMapper.deleteAppRolePermByIds(ids);
        return appRoleMapper.deleteAppRoleByIds(ids);
    }

    /**
     * 删除角色信息
     *
     * @param roleId 角色ID
     * @return 结果
     */
    @Override
    public int deleteAppRoleById(Long roleId) {
        return appRoleMapper.deleteAppRoleById(roleId);
    }

    /**
     * 查询所有角色
     *
     * @return 角色列表
     */
    @Override
    public List<AppRole> selectRoleAll() {
        return selectAppRoleList(new AppRole());
    }

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    @Override
    public List<AppRole> selectAppRolesByUserId(Long userId) {
        List<AppRole> userRoles = appRoleMapper.selectRoleByUserId(userId);
        List<AppRole> roles = selectRoleAll();
        // 标记当前用户的角色
        for (AppRole role : roles) {
            for (AppRole userRole : userRoles) {
                if (role.getRoleId().longValue() == userRole.getRoleId().longValue()) {
                    role.setFlag(true);
                    break;
                }
            }
        }
        return roles;
    }

    @Override
    public String checkRoleKeyUnique(AppRole role) {
        Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
        AppRole info = appRoleMapper.checkRoleKeyUnique(role.getRoleKey());
        if (StringUtils.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue()) {
            return Constants.NOT_UNIQUE;
        }
        return Constants.UNIQUE;
    }

    @Override
    public int updateAppRoleByRoleId(AppRole role) {
        return appRoleMapper.updateAppRole(role);
    }
}
