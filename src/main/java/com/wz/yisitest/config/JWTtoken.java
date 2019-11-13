package com.wz.yisitest.config;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author：wzxgd
 * @date：2019/10/28 --------------
 */
public class JWTtoken implements AuthenticationToken {
    //密钥
    private String token;

    public JWTtoken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return this.token;
    }

    @Override
    public Object getCredentials() {
        return this.token;
    }
}
