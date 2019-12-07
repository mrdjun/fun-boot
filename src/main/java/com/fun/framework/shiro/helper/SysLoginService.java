package com.fun.framework.shiro.helper;

import com.fun.common.constant.Constants;
import com.fun.common.constant.ShiroConstants;
import com.fun.framework.annotation.enums.LoginType;
import com.fun.common.exception.user.UserBlockedException;
import com.fun.common.exception.user.CaptchaException;
import com.fun.common.exception.user.UserNotExistsException;
import com.fun.common.exception.user.UserPasswordNotMatchException;
import com.fun.common.utils.encrypt.Md5Utils;
import com.fun.common.utils.MessageUtils;
import com.fun.common.utils.ServletUtils;
import com.fun.common.utils.StringUtils;
import com.fun.framework.manager.AsyncUtils;
import com.fun.project.admin.system.entity.user.AdminUser;
import com.fun.project.admin.system.entity.user.UserStatus;
import com.fun.project.admin.system.service.IAdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Admin登录处理
 *
 * @author DJun
 */
@Component
public class SysLoginService {

    @Autowired
    private IAdminUserService adminUserService;

    public AdminUser login(String loginName, String password) {

        // 1、验证验证码
        if (!StringUtils.isNull(ServletUtils.getRequest().getAttribute(ShiroConstants.CURRENT_CAPTCHA))) {
            AsyncUtils.excRecordLoginLog(ServletUtils.getRequest(), loginName, Constants.FAIL, LoginType.admin, MessageUtils.message("user.jcaptcha.error"));
            throw new CaptchaException();
        }

        // 2、验证账号密码格式是否正确
        if (StringUtils.isEmpty(loginName) || StringUtils.isEmpty(password)) {
            AsyncUtils.excRecordLoginLog(ServletUtils.getRequest(), loginName, Constants.FAIL, LoginType.admin, MessageUtils.message("not.null"));
            throw new UserNotExistsException();
        }

        // 长度不正确
        if (password.length() < 5 || password.length() > 20 || loginName.length() < 2 || loginName.length() > 20) {
            AsyncUtils.excRecordLoginLog(ServletUtils.getRequest(), loginName, Constants.FAIL, LoginType.admin, MessageUtils.message("user.password.not.match"));
            throw new UserPasswordNotMatchException();
        }

        // 3、查询数据库比对账号
        AdminUser user = adminUserService.selectUserByLoginName(loginName);
        if (StringUtils.isNull(user)) {
            AsyncUtils.excRecordLoginLog(ServletUtils.getRequest(), loginName, Constants.FAIL, LoginType.admin, MessageUtils.message("user.not.exists"));
            throw new UserNotExistsException();
        }
        // 4、验证用户状态是否异常
        if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
            AsyncUtils.excRecordLoginLog(ServletUtils.getRequest(), loginName, Constants.FAIL, LoginType.admin, MessageUtils.message("user.password.delete"));
            throw new UserBlockedException();
        }

        if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            AsyncUtils.excRecordLoginLog(ServletUtils.getRequest(), loginName, Constants.FAIL, LoginType.admin, MessageUtils.message("user.blocked"));
            throw new UserBlockedException();
        }

        // 5、验证密码
        if (!Md5Utils.validatePwd(user, password)) {
            AsyncUtils.excRecordLoginLog(ServletUtils.getRequest(), loginName, Constants.FAIL, LoginType.admin, MessageUtils.message("user.password.not.match"));
            throw new UserPasswordNotMatchException();
        }

        AsyncUtils.excRecordLoginLog(ServletUtils.getRequest(), loginName, Constants.SUCCESS, LoginType.admin, MessageUtils.message("user.login.success"));
        return user;
    }
}
