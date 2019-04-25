package com.bdth.oa.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * @author hj
 * @create 2019-04-24 20:37
    全局异常处理器
 */
@ControllerAdvice
@Slf4j
public class GlobalExcpeitonHandler {


    /**
     * 登录全局异常
     * @param e
     * @return
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private String authenticationHandler(AuthenticationException e) {
        log.warn("【登录失败】: {} ", e);
        return "/login";
    }

    /**
     * 用户名密码全局异常
     * @param e
     * @return
     */
    @ExceptionHandler(CredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private String credentialsHandler(CredentialsException e, Model model) {
        log.warn("【用户名，密码错误】: {} ", e);
        model.addAttribute("message", "用户名密码错误");
        return "/login";
    }

    /**
     * 权限资源全局异常
     * @param e
     * @return
     */
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    private ErrorTip authorizationHandler(AuthorizationException e, Model model) {
        log.warn("【无权限访问资源】: {} ", e);
        model.addAttribute("message", "没有访问权限");
        return ErrorTip.builder()
                .errorCode(HttpStatus.UNAUTHORIZED.value())
                .message(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .build();
    }




}
