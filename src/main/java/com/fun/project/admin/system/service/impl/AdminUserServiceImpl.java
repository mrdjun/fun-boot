package com.fun.project.admin.system.service.impl;

import com.fun.common.utils.Md5Utils;
import com.fun.common.utils.StringUtils;
import com.fun.common.utils.text.Convert;
import com.fun.framework.shiro.helper.ShiroUtils;
import com.fun.project.admin.system.entity.Role;
import com.fun.project.admin.system.mapper.RoleMapper;
import com.fun.project.admin.system.entity.user.AdminUser;
import com.fun.project.admin.system.entity.user.UserRole;
import com.fun.project.admin.system.mapper.AdminUserMapper;
import com.fun.project.admin.system.mapper.UserRoleMapper;
import com.fun.project.admin.system.service.IAdminUserService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * created by DJun on 2019/9/14 10:35
 * desc:
 */
@Service
public class AdminUserServiceImpl implements IAdminUserService {
    @Autowired
    private AdminUserMapper adminUserMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public List<AdminUser> selectUserList(AdminUser user, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return adminUserMapper.selectUserList(user);
    }

    @Override
    public AdminUser selectUserByLoginName(String loginName) {
        return adminUserMapper.selectUserByLoginName(loginName);
    }

    @Override
    public AdminUser selectUserByTelephone(String telephone) {
        return adminUserMapper.selectUserByTelephone(telephone);
    }

    @Override
    public AdminUser selectUserByEmail(String email) {
        return adminUserMapper.selectUserByEmail(email);
    }

    @Override
    public AdminUser selectUserById(Long userId) {
        return adminUserMapper.selectUserById(userId);
    }

    @Override
    public int deleteUserById(Long userId) {
        return adminUserMapper.deleteUserById(userId);
    }

    @Override
    public int deleteUserByIds(String ids) throws Exception {
        Long[] userIds = Convert.toLongArray(ids);
        return adminUserMapper.deleteUserByIds(userIds);
    }

    @Override
    public int insertUser(AdminUser user) {
        user.setCreateTime(System.currentTimeMillis());
        user.randomSalt();
        user.setPassword(Md5Utils.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        return adminUserMapper.insertUser(user);
    }

    @Override
    @Transactional
    public int updateUser(AdminUser user) {
        // 获取修改者
        Long userId = user.getUserId();
        if (StringUtils.isNotEmpty(ShiroUtils.getLoginName())) {
            user.setUpdateBy(ShiroUtils.getLoginName());
        }
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 新增用户与角色管理
        insertUserRole(user);
        return adminUserMapper.updateUser(user);
    }

    @Override
    public int updateUserInfo(AdminUser user) {
        return adminUserMapper.updateUser(user);
    }

    @Override
    public int updateUserInfoByLoginName(AdminUser user) {
        return adminUserMapper.updateUserInfoByLoginName(user);
    }

    @Override
    public int resetUserPwd(AdminUser user) {
        user.randomSalt();
        user.setPassword(Md5Utils.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        return adminUserMapper.updateUser(user);
    }

    @Override
    public int checkLoginNameUnique(String loginName) {
        return adminUserMapper.checkLoginNameUnique(loginName);
    }

    @Override
    public int checkPhoneUnique(String telephone) {
        return adminUserMapper.checkPhoneUnique(telephone);
    }

    @Override
    public int checkEmailUnique(String email) {
        return adminUserMapper.checkEmailUnique(email);
    }

    @Override
    public String selectUserRoleGroup(Long userId) {
        List<Role> list = roleMapper.selectRolesByUserId(userId);
        StringBuilder idsStr = new StringBuilder();
        for (Role role : list) {
            idsStr.append(role.getRoleName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString())) {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    @Override
    public int changeStatus(AdminUser user) {
        // 不可修改超级管理员的状态
        if (AdminUser.isAdmin(user.getUserId())) {
            return 0;
        }
        return adminUserMapper.updateUser(user);
    }

    /**
     * 新增用户角色信息
     *
     * @param user 用户对象
     */
    public void insertUserRole(AdminUser user) {
        Long[] roles = user.getRoleIds();
        if (StringUtils.isNotNull(roles)) {
            // 新增用户与角色管理
            List<UserRole> list = new ArrayList<>();
            for (Long roleId : user.getRoleIds()) {
                UserRole ur = new UserRole();
                ur.setUserId(user.getUserId());
                ur.setRoleId(roleId);
                list.add(ur);
            }
            if (list.size() > 0) {
                userRoleMapper.batchUserRole(list);
            }
        }
    }


}
