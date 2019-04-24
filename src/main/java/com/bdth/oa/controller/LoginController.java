package com.bdth.oa.controller;

import com.bdth.oa.shiro.ShiroKit;
import com.bdth.oa.shiro.ShiroUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

/**
 * @author hj
 * @create 2019-04-23 11:44
 */
@Controller
@Slf4j
public class LoginController {


    @PostMapping("/login")
    public String login(String username, String password, HttpSession session) {
        try {
            Subject subject = ShiroKit.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            //开始认证
            subject.login(token);
            //获取封装的user
            ShiroUser user = ShiroKit.getUser();
            //设置当前用户信息到session中
            session.setAttribute("shiroUser", user);
        } catch (AuthenticationException e) {
            log.warn("【登录失败，==> {} 】", e);
            return "login";
        }
        return "redirect:/";
    }
}
