package com.wz.yisitest.controller;


import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 文章评论表 前端控制器
 * </p>
 *
 * @author yisi
 * @since 2019-10-17
 */
@Controller
@RequestMapping("/context")
public class ContextController {
    //注解验角色和权限

    @ResponseBody
    @RequestMapping("/show")
    @RequiresPermissions("user:list")
    public String index() {
        return "文章访问成功牛逼！";
    }
}

