package com.wz.yisitest.controller;

import com.wz.yisitest.Response.ResponseData;
import com.wz.yisitest.Response.ResponseDataUtil;
import com.wz.yisitest.config.UnauthorizedException;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author：wzxgd
 * @date：2019/10/30 --------------
 */
@RestControllerAdvice
public class ExceptionController {
    // 捕捉shiro的异常
    @ExceptionHandler(ShiroException.class)
    public ResponseData handle401(ShiroException e) {
        return ResponseDataUtil.buildError("401", e.getMessage(), null);
    }

    // 捕捉UnauthorizedException
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseData handle401() {
        return ResponseDataUtil.buildError("401", "Unauthorized", null);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseData tokenFaild() {
        System.out.println("ssssssssss");
        return ResponseDataUtil.buildError("Token过期,请重新登录");
    }
}
