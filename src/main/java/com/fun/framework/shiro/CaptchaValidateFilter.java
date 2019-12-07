package com.fun.framework.shiro;

import com.fun.common.utils.StringUtils;
import com.fun.common.constant.ShiroConstants;
import com.fun.framework.shiro.helper.ShiroUtils;
import com.google.code.kaptcha.Constants;
import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Captcha 验证码过滤器
 * @author DJun
 */
@Setter
@Getter
@PropertySource("classpath:fun-boot.properties")
public class CaptchaValidateFilter extends AccessControlFilter {
    /** 是否开启验证码 */
    @Value("${shiro.captchaEnabled}")
    private boolean captchaEnabled;

    /**  验证码类型 */
    @Value("${shiro.captchaType}")
    private String captchaType;

    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        request.setAttribute(ShiroConstants.CURRENT_ENABLED, captchaEnabled);
        request.setAttribute(ShiroConstants.CURRENT_TYPE, captchaType);
        return super.onPreHandle(request, response, mappedValue);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        // 验证码禁用 或不是表单提交 允许访问
        if (!captchaEnabled || !"post".equals(httpServletRequest.getMethod().toLowerCase())) {
            return true;
        }
        return validateResponse(httpServletRequest, httpServletRequest.getParameter(ShiroConstants.CURRENT_VALIDATECODE));
    }

    public boolean validateResponse(HttpServletRequest request, String validateCode) {
        Object obj = ShiroUtils.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        String code = String.valueOf(obj != null ? obj : "");
        return !StringUtils.isEmpty(validateCode) && validateCode.equalsIgnoreCase(code);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        request.setAttribute(ShiroConstants.CURRENT_CAPTCHA, ShiroConstants.CAPTCHA_ERROR);
        return true;
    }
}
