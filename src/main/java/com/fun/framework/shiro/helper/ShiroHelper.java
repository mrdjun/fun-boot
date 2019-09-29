package com.fun.framework.shiro.helper;

import com.fun.framework.shiro.ShiroRealm;
import org.apache.shiro.authz.AuthorizationInfo;

/**
 * @author DJun
 */
public class ShiroHelper extends ShiroRealm {

    /**
     * 获取当前用户的角色和权限集合
     *
     * @return AuthorizationInfo
     */
    public AuthorizationInfo getCurrentUserAuthorizationInfo() {
        return super.doGetAuthorizationInfo(null);
    }
}
