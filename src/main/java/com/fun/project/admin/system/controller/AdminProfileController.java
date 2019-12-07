package com.fun.project.admin.system.controller;

import com.fun.common.constant.Constants;
import com.fun.common.result.CommonResult;
import com.fun.common.utils.encrypt.Md5Utils;
import com.fun.common.utils.StringUtils;
import com.fun.common.utils.file.FileUploadUtils;
import com.fun.framework.annotation.Log;
import com.fun.framework.config.FunBootConfig;
import com.fun.framework.web.controller.AdminBaseController;
import com.fun.project.admin.system.entity.user.AdminUser;
import com.fun.project.admin.system.service.IAdminUserService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.fun.common.result.CommonResult.failed;
import static com.fun.common.result.CommonResult.success;

/**
 *
 * @author DJun
 * @date 2019/11/4
 */
@Api(tags = {"admin个人信息"})
@Controller
@RequestMapping("/admin/system/user/profile")
public class AdminProfileController extends AdminBaseController {
    private static final Logger log = LoggerFactory.getLogger(AdminProfileController.class);
    private String prefix = "system/user/profile";
    @Autowired
    private IAdminUserService userService;

    /**
     * 个人信息
     */
    @GetMapping()
    public String profile(ModelMap mmap) {
        AdminUser user = getSysUser();
        mmap.put("user", user);
        mmap.put("roleGroup", userService.selectUserRoleGroup(user.getUserId()));
        mmap.put("postGroup", userService.selectUserPostGroup(user.getUserId()));
        return view(prefix + "/profile");
    }

    @GetMapping("/checkPassword")
    @ResponseBody
    public boolean checkPassword(String password) {
        AdminUser user = getSysUser();
        return matches(user, password);
    }

    @GetMapping("/resetPwd")
    public String resetPwd(ModelMap mmap) {
        AdminUser user = getSysUser();
        mmap.put("user", userService.selectAdminUserById(user.getUserId()));
        return view(prefix + "/resetPwd");
    }

    @Log("重置密码")
    @PostMapping("/resetPwd")
    @ResponseBody
    public CommonResult resetPwd(String oldPassword, String newPassword) {
        AdminUser user = getSysUser();
        if (StringUtils.isNotEmpty(newPassword) && matches(user, oldPassword)) {
            user.setPassword(newPassword);
            if (userService.resetUserPwd(user) > 0) {
                setSysUser(userService.selectAdminUserById(user.getUserId()));
                return success(Constants.SUCCESS);
            }
            return failed();
        } else {
            return failed("修改密码失败，旧密码错误");
        }

    }

    /**
     * 修改用户
     */
    @GetMapping("/edit")
    public String edit(ModelMap mmap) {
        AdminUser user = getSysUser();
        mmap.put("user", userService.selectAdminUserById(user.getUserId()));
        return view(prefix + "/edit");
    }

    /**
     * 修改头像
     */
    @GetMapping("/avatar")
    public String avatar(ModelMap mmap) {
        AdminUser user = getSysUser();
        mmap.put("user", userService.selectAdminUserById(user.getUserId()));
        return view(prefix + "/avatar");
    }

    @Log("修改个人信息")
    @PostMapping("/update")
    @ResponseBody
    public CommonResult update(AdminUser user) {
        AdminUser currentUser = getSysUser();
        currentUser.setUsername(user.getUsername());
        currentUser.setEmail(user.getEmail());
        currentUser.setTelephone(user.getTelephone());
        currentUser.setSex(user.getSex());
        if (userService.updateUserInfo(currentUser) > 0) {
            setSysUser(userService.selectAdminUserById(currentUser.getUserId()));
            return success(Constants.SUCCESS);
        }
        return failed();
    }

    @Log("修改头像")
    @PostMapping("/updateAvatar")
    @ResponseBody
    public CommonResult updateAvatar(@RequestParam("avatarfile") MultipartFile file) {
        AdminUser currentUser = getSysUser();
        try {
            if (!file.isEmpty()) {
                String avatar = FileUploadUtils.upload(FunBootConfig.getAvatarPath(), file);
                currentUser.setAvatar(avatar);
                if (userService.updateUserInfo(currentUser) > 0) {
                    setSysUser(userService.selectAdminUserById(currentUser.getUserId()));
                    return success(Constants.SUCCESS);
                }
            }
            return failed();
        } catch (Exception e) {
            log.error("修改头像失败！", e);
            return failed(e.getMessage());
        }
    }

    private boolean matches(AdminUser user, String newPassword) {
        return user.getPassword().equals(Md5Utils.encryptPassword(user.getLoginName(), newPassword, user.getSalt()));
    }

}
