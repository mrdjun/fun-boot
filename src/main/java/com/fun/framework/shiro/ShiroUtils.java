package com.fun.framework.shiro;

import com.fun.common.utils.BeanUtils;
import com.fun.common.utils.StringUtils;
import com.fun.project.admin.system.user.entity.AdminUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;

/**
 * created by DJun on 2019/9/14 10:23
 * desc: shiro 工具类
 */
public class ShiroUtils {

    // 获取主体
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    // 获取Session
    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    // 退出登录
    public static void logout() {
        getSubject().logout();
    }

    // 获取当前用户
    public static AdminUser getSysUser() {
        AdminUser user = null;
        Object obj = getSubject().getPrincipal();
        if (StringUtils.isNotNull(obj)) {
            user = new AdminUser();
            BeanUtils.copyBeanProp(user, obj);
        }
        return user;
    }

    public static void setSysUser(AdminUser user) {
        Subject subject = getSubject();
        PrincipalCollection principalCollection = subject.getPrincipals();
        String realmName = principalCollection.getRealmNames().iterator().next();
        PrincipalCollection newPrincipalCollection = new SimplePrincipalCollection(user, realmName);
        // 重新加载Principal
        subject.runAs(newPrincipalCollection);
    }

    // 清理授权缓存
    public static void clearCachedAuthorizationInfo() {
        RealmSecurityManager rsm = (RealmSecurityManager) SecurityUtils.getSecurityManager();
        ShiroRealm realm = (ShiroRealm) rsm.getRealms().iterator().next();
        realm.clearCached();
    }

    public static Long getUserId() {
        return getSysUser().getUserId();
    }

    public static String getLoginName() {
        return getSysUser().getLoginName();
    }

    public static String getIp() {
        return getSubject().getSession().getHost();
    }

    public static String getSessionId() {
        return String.valueOf(getSubject().getSession().getId());
    }


}
