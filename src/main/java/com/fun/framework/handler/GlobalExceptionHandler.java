package com.fun.framework.handler;

import com.fun.common.exception.base.BusinessException;
import com.fun.common.exception.file.FileDownloadException;
import com.fun.common.exception.FunBootException;
import com.fun.common.exception.LimitAccessException;
import com.fun.common.exception.user.UserPasswordRetryLimitExceedException;
import com.fun.common.utils.PermissionUtils;
import com.fun.common.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.session.ExpiredSessionException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.List;
import java.util.Set;

/**
 * 捕获全项目异常处理
 *
 * @author DJun
 */
@Slf4j
@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    /** 系统异常HttpRequestMethodNotSupportedException */
    @ExceptionHandler(value = Exception.class)
    public FunBootResponse handleException(Exception e) {
        log.error("系统内部异常，异常信息", e);
        return new FunBootResponse().code(HttpStatus.INTERNAL_SERVER_ERROR).message("服务器错误，请联系管理员");
    }

    @ExceptionHandler(value = FunBootException.class)
    public FunBootResponse handleFunBootException(FunBootException e) {
        log.error("系统异常", e);
        return new FunBootResponse().code(HttpStatus.INTERNAL_SERVER_ERROR).message(e.getMessage());
    }

    /** 权限校验失败 如果请求为ajax返回json，普通请求跳转页面 */
    @ExceptionHandler(AuthorizationException.class)
    public Object handleAuthorizationException(HttpServletRequest request, AuthorizationException e) {
        log.error(e.getMessage(), e);
        if (ServletUtils.isAjaxRequest(request)) {
            return new FunBootResponse().code(HttpStatus.FORBIDDEN).message(PermissionUtils.getMsg(e.getMessage()));
        } else {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("/error/403");
            return modelAndView;
        }
    }

    /** 请求方式不支持 */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public FunBootResponse handleException(HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage(), e);
        return new FunBootResponse().code(HttpStatus.INTERNAL_SERVER_ERROR).message("不支持' " + e.getMethod() + "'请求");
    }

    /**
     * 统一处理请求参数校验(实体对象传参)
     *
     * @param e BindException
     * @return FunBootResponse
     */
    @ExceptionHandler(BindException.class)
    public FunBootResponse validExceptionHandler(BindException e) {
        StringBuilder message = new StringBuilder();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        for (FieldError error : fieldErrors) {
            message.append(error.getField()).append(error.getDefaultMessage()).append(",");
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));
        return new FunBootResponse().code(HttpStatus.BAD_REQUEST).message(message.toString());
    }

    /** 业务异常 */
    @ExceptionHandler(BusinessException.class)
    public Object businessException(HttpServletRequest request, BusinessException e) {
        log.error(e.getMessage(), e);
        if (ServletUtils.isAjaxRequest(request)) {
            return new FunBootResponse().code(HttpStatus.INTERNAL_SERVER_ERROR).message(e.getMessage());
        } else {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("errorMessage", e.getMessage());
            modelAndView.setViewName("error/business");
            return modelAndView;
        }
    }

    /**
     * 统一处理请求参数校验(普通传参)
     *
     * @param e ConstraintViolationException
     * @return FunBootResponse
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public FunBootResponse handleConstraintViolationException(ConstraintViolationException e) {
        StringBuilder message = new StringBuilder();
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            Path path = violation.getPropertyPath();
            String[] pathArr = StringUtils.splitByWholeSeparatorPreserveAllTokens(path.toString(), ".");
            message.append(pathArr[1]).append(violation.getMessage()).append(",");
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));
        return new FunBootResponse().code(HttpStatus.BAD_REQUEST).message(message.toString());
    }

    @ExceptionHandler(value = LimitAccessException.class)
    public FunBootResponse handleLimitAccessException(LimitAccessException e) {
        log.error("LimitAccessException", e);
        return new FunBootResponse().code(HttpStatus.TOO_MANY_REQUESTS).message(e.getMessage());
    }

    @ExceptionHandler(value = UnauthorizedException.class)
    public FunBootResponse handleUnauthorizedException(UnauthorizedException e) {
        log.error("UnauthorizedException", e);
        return new FunBootResponse().code(HttpStatus.FORBIDDEN).message(e.getMessage());
    }

    @ExceptionHandler(value = ExpiredSessionException.class)
    public FunBootResponse handleExpiredSessionException(ExpiredSessionException e) {
        log.error("ExpiredSessionException", e);
        return new FunBootResponse().code(HttpStatus.UNAUTHORIZED).message(e.getMessage());
    }

    @ExceptionHandler(value = FileDownloadException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleFileDownloadException(FileDownloadException e) {
        log.error("FileDownloadException", e);
    }

    /** admin密码错误次数过多 */
    @ExceptionHandler(value = UserPasswordRetryLimitExceedException.class)
    public FunBootResponse handleUserPasswordRetryLimitExceedException(UserPasswordRetryLimitExceedException e) {
        return new FunBootResponse().code(HttpStatus.INTERNAL_SERVER_ERROR).message(e.getMessage());
    }

    /** 拦截未知的运行时异常 */
    @ExceptionHandler(RuntimeException.class)
    public FunBootResponse notFount(RuntimeException e) {
        log.error("运行时异常:", e);
        return new FunBootResponse().code(HttpStatus.INTERNAL_SERVER_ERROR).message("运行时异常:" + e.getMessage());
    }
}
