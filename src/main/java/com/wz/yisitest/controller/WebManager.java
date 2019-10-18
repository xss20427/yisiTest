package com.wz.yisitest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author：yisi
 * @date：17/10/2019 --------------
 */
@Controller
public class WebManager {

    //返回登陆页面
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String GetIndexHtml() {
        return "index";
    }

    //返回注册页面
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String GetRegisterHtml() {
        return "register";
    }

    //返回主页
    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public String GetMenuHtml() {
        return "menu";
    }
}
