package com.wz.yisitest.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wz.yisitest.Response.ResponseData;
import com.wz.yisitest.Response.ResponseDataUtil;
import com.wz.yisitest.config.UnauthorizedException;
import com.wz.yisitest.entity.User;
import com.wz.yisitest.service.impl.UserServiceImpl;
import com.wz.yisitest.util.JWTUtil;
import com.wz.yisitest.util.SnowFlake;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author yisi
 * @since 2019-10-17
 */
@Api(value = "/user", tags = "用户接口模块")
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @ApiOperation(value = "/登陆接口", notes = "传入用户名密码校验", httpMethod = "POST")
    @ApiImplicitParams({@ApiImplicitParam(name = "userName", required = true, value = "用户名", dataType = "String"),
            @ApiImplicitParam(name = "passWorld", required = true, dataType = "String")})
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseData login(@RequestParam(value = "userName", defaultValue = "") String username, @RequestParam(value = "passWorld", defaultValue = "") String password) {
        System.out.println("输入：" + username + "  " + password);
        User user = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUser, username));
        if (user != null && user.getPassword().equals(password)) {
            return ResponseDataUtil.buildSuccess(JWTUtil.sign(user.getUser(), user.getPassword()));
        } else {
            throw new UnauthorizedException();
        }
    }


    @ApiOperation(value = "是否为管理员", notes = "校验是否为管理员", httpMethod = "POST")
    @RequestMapping(value = "/whoami")
    public boolean whoami() {
        Subject SecurityUser = SecurityUtils.getSubject();
        if (SecurityUser.hasRole("admin")) {
            return true;
        }
        return false;
    }

    @ApiOperation(value = "/注册接口", notes = "提供注册接口", httpMethod = "POST")
    @ApiImplicitParams({@ApiImplicitParam(name = "userName", required = true, value = "用户名", dataType = "String"),
            @ApiImplicitParam(name = "passWorld", required = true, dataType = "String"),
            @ApiImplicitParam(name = "nickName", required = false, dataType = "String")})
    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseData register(@RequestParam("userName") String username,
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
            return ResponseDataUtil.buildError("臭弟弟注册失败了");
        } else {
            return ResponseDataUtil.buildSuccess("注册成功");
        }
    }

    @ApiOperation(value = "/无权限", notes = "无权限跳转的接口", httpMethod = "POST")
    @RequestMapping(path = "/error")
    public ResponseData unauthorized() {
        return ResponseDataUtil.buildError("臭弟弟 你没有权限");
    }
}

