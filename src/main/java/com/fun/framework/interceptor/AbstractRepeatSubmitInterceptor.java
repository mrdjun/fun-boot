package com.fun.framework.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.fun.common.result.R;
import com.fun.common.utils.MessageUtils;
import com.fun.common.utils.ServletUtils;
import com.fun.framework.annotation.RepeatSubmit;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 防止表单重复提交抽象类
 *
 * @author DJun
 */
@Component
public abstract class AbstractRepeatSubmitInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            RepeatSubmit annotation = method.getAnnotation(RepeatSubmit.class);
            if (annotation != null) {
                if (this.isRepeatSubmit(request)) {
                    R<String> r = R.failed(MessageUtils.message("sys.busy"));
                    ServletUtils.renderString(response, JSONObject.toJSONString(r));
                    return false;
                }
            }
            return true;
        } else {
            return super.preHandle(request, response, handler);
        }
    }

    /**
     * 验证是否重复提交由子类实现具体的防重复提交的规则
     *
     * @param request HttpServletRequest
     * @return boolean
     */
    public abstract boolean isRepeatSubmit(HttpServletRequest request);
}