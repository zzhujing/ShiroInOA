package com.bdth.oa.controller;

import com.bdth.oa.controller.request.LoginRequest;
import com.bdth.oa.shiro.ShiroKit;
import com.bdth.oa.shiro.ShiroUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author hj
 * @create 2019-04-23 11:44
 */
@Controller
@Slf4j
public class LoginController {


    /**
     * 跳转到登录页面
     * @param session session
     * @return view视图名
     */
    @GetMapping("/login")
    public String showLoginPage(HttpSession session) {
        if (ShiroKit.isAuthenticated() && session.getAttribute("shiroUser") != null) {
            return "redirect:/";
        }
        return "login";
    }


    /**
     * 登录
     * @param request 登录请求参数
     * @param session  session
     * @return view视图名
     */
    @PostMapping("/login")
    public String login(@Valid LoginRequest request, BindingResult bindingResult, HttpSession session, Model model) {
        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("message", bindingResult.getFieldError().getDefaultMessage());
                return "login";
            }
            Subject subject = ShiroKit.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(request.getUsername(), request.getPassword());
            //开始认证
            subject.login(token);
            //获取封装的user
            ShiroUser user = ShiroKit.getUser();
            //设置当前用户信息到session中
            session.setAttribute("shiroUser", user);
        } catch (AuthenticationException e) {
            log.warn("【登录失败，==> {} 】", e);
            model.addAttribute("message", "登录失败");
            return "login";
        }
        return "redirect:/";
    }
}
