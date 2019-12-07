package com.fun.project.admin.system.service.impl;

import com.fun.common.constant.Constants;
import com.fun.common.exception.FunBootException;
import com.fun.common.exception.base.BusinessException;
import com.fun.common.utils.encrypt.Md5Utils;
import com.fun.common.utils.StringUtils;
import com.fun.common.utils.TimestampUtil;
import com.fun.common.utils.text.Convert;
import com.fun.framework.shiro.helper.ShiroUtils;
import com.fun.project.admin.system.entity.Post;
import com.fun.project.admin.system.entity.role.Role;
import com.fun.project.admin.system.entity.user.UserPost;
import com.fun.project.admin.system.mapper.*;
import com.fun.project.admin.system.entity.user.AdminUser;
import com.fun.project.admin.system.entity.user.UserRole;
import com.fun.project.admin.system.service.IAdminUserService;
import com.fun.project.admin.system.service.IConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 管理员用户
 *
 * @author DJun
 */
@Service
public class AdminUserServiceImpl implements IAdminUserService {
    private Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private AdminUserMapper adminUserMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserPostMapper userPostMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private IConfigService configService;

    @Override
    public List<AdminUser> selectAdminUserList(AdminUser user) {
        return adminUserMapper.selectAdminUserList(user);
    }

    @Override
    public AdminUser selectUserByLoginName(String loginName) {
        return adminUserMapper.selectUserByLoginName(loginName);
    }

    @Override
    public AdminUser selectUserByTelephone(String telephone) {
        return adminUserMapper.selectAdminUserByTelephone(telephone);
    }

    @Override
    public AdminUser selectUserByEmail(String email) {
        return adminUserMapper.selectUserByEmail(email);
    }

    @Override
    public AdminUser selectAdminUserById(Long userId) {
        return adminUserMapper.selectAdminUserById(userId);
    }

    @Override
    public int deleteAdminUserById(Long userId) {
        return adminUserMapper.deleteAdminUserById(userId);
    }

    @Override
    public int deleteAdminUserByIds(String ids) throws Exception {
        Long[] userIds = Convert.toLongArray(ids);
        return adminUserMapper.deleteAdminUserByIds(userIds);
    }

    @Override
    public int insertAdminUser(AdminUser user) {
        user.setCreateTime(System.currentTimeMillis());
        if (StringUtils.isNotEmpty(ShiroUtils.getLoginName())) {
            user.setCreateBy(ShiroUtils.getLoginName());
        }
        user.randomSalt();
        user.setPassword(Md5Utils.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        // 新增用户信息
        int rows = adminUserMapper.insertAdminUser(user);
        // 新增用户岗位关联
        insertAdminUserPost(user);
        // 新增用户与角色管理
        insertUserRole(user);
        return rows;
    }

    @Override
    @Transactional(rollbackFor = FunBootException.class)
    public int updateAdminUser(AdminUser user) {
        // 获取修改者
        Long userId = user.getUserId();
        user.setUpdateTime(TimestampUtil.getCurrentTimestamp13());
        if (StringUtils.isNotEmpty(ShiroUtils.getLoginName())) {
            user.setUpdateBy(ShiroUtils.getLoginName());
        }
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 新增用户与角色管理
        insertUserRole(user);
        // 删除用户与岗位关联
        userPostMapper.deleteUserPostByUserId(userId);
        // 新增用户与岗位管理
        insertAdminUserPost(user);
        return adminUserMapper.updateAdminUser(user);
    }

    /**
     * 修改用户个人详细信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserInfo(AdminUser user) {
        return adminUserMapper.updateAdminUser(user);
    }

    @Override
    public int updateUserInfoByLoginName(AdminUser user) {
        return adminUserMapper.updateUserInfoByLoginName(user);
    }

    @Override
    public int resetUserPwd(AdminUser user) {
        user.randomSalt();
        user.setPassword(Md5Utils.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        return adminUserMapper.updateAdminUser(user);
    }

    @Override
    public String checkLoginNameUnique(String loginName) {

        int count = adminUserMapper.checkLoginNameUnique(loginName);
        if (count > 0) {
            return Constants.NOT_UNIQUE;
        }
        return Constants.UNIQUE;

    }

    @Override
    public String checkPhoneUnique(AdminUser user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        AdminUser info = adminUserMapper.checkPhoneUnique(user.getTelephone());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return Constants.NOT_UNIQUE;
        }
        return Constants.UNIQUE;
    }

    @Override
    public String checkEmailUnique(AdminUser user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        AdminUser info = adminUserMapper.checkEmailUnique(user.getEmail());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return Constants.NOT_UNIQUE;
        }
        return Constants.UNIQUE;
    }

    @Override
    public String selectUserRoleGroup(Long userId) {
        List<Role> list = roleMapper.selectRolesByUserId(userId);
        StringBuilder idsStr = new StringBuilder();
        for (Role role : list) {
            idsStr.append(role.getRoleName()).append("," );
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
        return adminUserMapper.updateAdminUser(user);
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

    /**
     * 根据条件分页查询已分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    public List<AdminUser> selectAllocatedList(AdminUser user) {
        return adminUserMapper.selectAllocatedList(user);
    }

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    public List<AdminUser> selectUnallocatedList(AdminUser user) {
        return adminUserMapper.selectUnallocatedList(user);
    }

    @Override
    public String selectUserPostGroup(Long userId) {
        List<Post> list = postMapper.selectPostsByUserId(userId);
        StringBuffer idsStr = new StringBuffer();
        for (Post post : list) {
            idsStr.append(post.getPostName()).append("," );
        }
        if (StringUtils.isNotEmpty(idsStr.toString())) {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    @Override
    public String importUser(List<AdminUser> userList, Boolean isUpdateSupport) {
        if (StringUtils.isNull(userList) || userList.size() == 0) {
            throw new BusinessException("导入用户数据不能为空！" );
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String operName = ShiroUtils.getLoginName();
        String password = configService.selectConfigByKey("sys.user.initPassword" );
        for (AdminUser user : userList) {
            try {
                // 验证是否存在这个用户
                AdminUser u = adminUserMapper.selectUserByLoginName(user.getLoginName());
                if (StringUtils.isNull(u)) {
                    user.setPassword(password);
                    user.setCreateBy(operName);
                    this.insertAdminUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getLoginName() + " 导入成功" );
                } else if (isUpdateSupport) {
                    user.setUpdateBy(operName);
                    this.updateAdminUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getLoginName() + " 更新成功" );
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、账号 " + user.getLoginName() + " 已存在" );
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + user.getLoginName() + " 导入失败：" ;
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：" );
            throw new BusinessException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：" );
        }
        return successMsg.toString();
    }

    /**
     * 新增用户岗位信息
     *
     * @param user 用户对象
     */
    private void insertAdminUserPost(AdminUser user) {
        Long[] posts = user.getPostIds();
        if (StringUtils.isNotNull(posts)) {
            // 新增用户与岗位管理
            List<UserPost> list = new ArrayList<>();
            for (Long postId : user.getPostIds()) {
                UserPost up = new UserPost();
                up.setUserId(user.getUserId());
                up.setPostId(postId);
                list.add(up);
            }
            if (list.size() > 0) {
                userPostMapper.batchUserPost(list);
            }
        }
    }

}
