package com.wz.yisitest.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wz.yisitest.entity.User;
import com.wz.yisitest.service.impl.UserServiceImpl;
import com.wz.yisitest.util.SnowFlake;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author yisi
 * @since 2019-10-17
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam(value = "userName", defaultValue = "") String username, @RequestParam(value = "passWorld", defaultValue = "") String password, Model model) {
        if (username.equals("") || password.equals("")) return "loginfaild";
//        从SecurityUtils里边创建一个 subject
        Subject subject = SecurityUtils.getSubject();
        // 在认证提交前准备 token（令牌）
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        // 执行认证登陆
        try {
            subject.login(token);
        } catch (AuthenticationException ice) {
            return "loginfaild";
        }
        if (subject.isAuthenticated()) {
            User user = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUser, username));
            model.addAttribute("whoami", user.getDisplayName());
            return "menu";
        } else {
            token.clear();
            return "loginfaild";
        }
    }

    @ResponseBody
    @RequiresPermissions("user:*")
    @RequestMapping(value = "/whoami")
    public boolean whoami() {
        Subject SecurityUser = SecurityUtils.getSubject();
        if (SecurityUser.hasRole("admin")) {
            return true;
        }
        return false;
    }

    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestParam("userName") String username,
                           @RequestParam("passWorld") String password,
                           @RequestParam(value = "nickName", required = false, defaultValue = "默认名称") String nickName) {
        User u = new User();
        try {
            u.setId(SnowFlake.generator());
            u.setUser(username);
            u.setPassword(password);
            u.setDisplayName(nickName);
            u.setCreateDate(LocalDateTime.now());
            u.setStatus(true);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("雪花算法生成错误");
        }
        ;
        int code = userService.registerUser(u);
        if (code == -1) {
            return "Faild";
        } else {
            return "Succes";
        }
    }
}

