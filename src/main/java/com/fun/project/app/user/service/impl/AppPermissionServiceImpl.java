package com.fun.project.app.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.fun.common.constant.Constants;
import com.fun.common.exception.FunBootException;
import com.fun.common.utils.StringUtils;
import com.fun.common.utils.TimestampUtil;
import com.fun.framework.shiro.helper.ShiroUtils;
import com.fun.framework.web.entity.Ztree;
import com.fun.project.app.user.entity.AppRolePerm;
import com.fun.project.app.user.mapper.AppRolePermMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fun.project.app.user.mapper.AppPermissionMapper;
import com.fun.project.app.user.entity.AppPermission;
import com.fun.project.app.user.service.IAppPermissionService;
import com.fun.common.utils.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 权限
 *
 * @author DJun
 * @date 2019-12-02
 */
@Service
public class AppPermissionServiceImpl implements IAppPermissionService {
    @Autowired
    private AppPermissionMapper appPermissionMapper;
    @Autowired
    private AppRolePermMapper appRolePermMapper;

    /**
     * 查询权限
     *
     * @param permId 权限ID
     * @return 权限
     */
    @Override
    public AppPermission selectAppPermissionById(Long permId) {
        return appPermissionMapper.selectAppPermissionById(permId);
    }

    /**
     * 查询权限列表
     *
     * @param appPermission 权限
     * @return 权限
     */
    @Override
    public List<AppPermission> selectAppPermissionList(AppPermission appPermission) {
        return appPermissionMapper.selectAppPermissionList(appPermission);
    }

    /**
     * 新增权限
     *
     * @param appPermission 权限
     * @return 结果
     */
    @Override
    public int insertAppPermission(AppPermission appPermission) {
        appPermission.setCreateTime(TimestampUtil.getCurrentTimestamp13());
        appPermission.setCreateBy(ShiroUtils.getLoginName());
        int res = appPermissionMapper.insertAppPermission(appPermission);
        if (StringUtils.isNotNull(appPermission.getPermId()) && res > 0) {
            Long permId = appPermission.getPermId();
            AppRolePerm rolePerm = new AppRolePerm();
            rolePerm.setPermId(permId);
            rolePerm.setRoleId(appPermission.getRoleId());
            appRolePermMapper.insertAppRolePerm(rolePerm);
        }
        return res;
    }

    /**
     * 修改权限
     *
     * @param perm 权限
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = FunBootException.class)
    public int updateAppPermission(AppPermission perm) {
        perm.setUpdateTime(TimestampUtil.getCurrentTimestamp13());
        perm.setUpdateBy(ShiroUtils.getLoginName());
        return appPermissionMapper.updateAppPermission(perm);
    }

    /**
     * 删除权限对象
     *
     * @param ids 需要删除的 permId
     * @return 结果
     */
    @Override
    public int deleteAppPermissionByIds(String ids) {
        Long[] idLongArr = Convert.toLongArray(ids);
        // 删除已分配给角色的所有当前的权限字符串
        for (Long permId : idLongArr) {
            long[] roleIds = appRolePermMapper.selectRoleIdByPermId(permId);
            if (StringUtils.isNotNull(roleIds) && roleIds.length > 0) {
                for (long roleId : roleIds) {
                    appRolePermMapper.deleteWithRolePerm(roleId, permId);
                }
            }
        }
        return appPermissionMapper.deleteAppPermissionByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除权限信息
     *
     * @param permId 权限ID
     * @return 结果
     */
    @Override
    public int deleteAppPermissionById(Long permId) {
        return appPermissionMapper.deleteAppPermissionById(permId);
    }

    @Override
    public String checkPermUnique(AppPermission permission) {
        Long permId = StringUtils.isNull(permission.getPermId()) ? -1L : permission.getPermId();
        AppPermission info = appPermissionMapper.checkPermUnique(permission.getPerm());
        if (StringUtils.isNotNull(info) && info.getPermId().longValue() != permId.longValue()) {
            return Constants.NOT_UNIQUE;
        }
        return Constants.UNIQUE;
    }

    @Override
    public List<Ztree> rolePermTreeData(AppPermission permission) {
        Long roleId = permission.getRoleId();
        List<Ztree> ztrees;
        List<AppPermission> permissionList = selectAppPermissionList(new AppPermission());
        if (StringUtils.isNotNull(roleId)) {
            List<String> rolePermList = appPermissionMapper.selectAppPermsTree(roleId);
            ztrees = initZtree(permissionList, rolePermList, true);
        } else {
            ztrees = initZtree(permissionList, null, true);
        }
        return ztrees;
    }

    /**
     * 查询所有权限
     *
     * @return 菜单列表
     */
    @Override
    public List<Ztree> permTreeData() {
        List<AppPermission> permissionList = selectAppPermissionList(new AppPermission());
        return initZtree(permissionList);
    }

    /**
     * 对象转菜单树
     *
     * @param menuList 菜单列表
     * @return 树结构列表
     */
    private List<Ztree> initZtree(List<AppPermission> menuList) {
        return initZtree(menuList, null, false);
    }

    /**
     * 对象转菜单树
     *
     * @param permissionList 权限列表
     * @param roleMenuList   角色已存在菜单列表
     * @param permsFlag      是否需要显示权限标识
     * @return 树结构列表
     */
    private List<Ztree> initZtree(List<AppPermission> permissionList, List<String> roleMenuList, boolean permsFlag) {
        List<Ztree> ztrees = new ArrayList<>();
        boolean isCheck = StringUtils.isNotNull(roleMenuList);
        for (AppPermission perm : permissionList) {
            Ztree ztree = new Ztree();
            ztree.setId(perm.getPermId());
            ztree.setName(transMenuName(perm, permsFlag));
            ztree.setTitle(perm.getPermName());
            if (isCheck) {
                ztree.setChecked(roleMenuList.contains(perm.getPermId() + perm.getPerm()));
            }
            ztrees.add(ztree);
        }
        return ztrees;
    }

    private String transMenuName(AppPermission permission, boolean permsFlag) {
        StringBuilder sb = new StringBuilder();
        sb.append(permission.getPermName());
        if (permsFlag) {
            sb.append("<fonts color=\"#888\">&nbsp;&nbsp;&nbsp;").append(permission.getPerm()).append("</fonts>");
        }
        return sb.toString();
    }

}