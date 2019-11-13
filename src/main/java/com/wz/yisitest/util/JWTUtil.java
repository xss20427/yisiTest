package com.wz.yisitest.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @author：wzxgd
 * @date：2019/10/28 --------------
 */
public class JWTUtil {
    private static final long EXPIRE_TIME = 60 * 10 * 1000;//设置过期时间十分钟

    /**
     * 校验token是否正确
     *
     * @param token
     * @param userName
     * @param passWord
     * @return
     */
    public static boolean verify(String token, String userName, String passWord) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(passWord);
            JWTVerifier verifier = JWT.require(algorithm).withClaim("username", userName).build();
            verifier.verify(token);
            return true;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (TokenExpiredException e) {

        }
        return false;
    }

    /**
     * 获取自己的用户名
     *
     * @param token
     * @return
     */
    public static String getUserName(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    public static String sign(String userName, String passWorld) {
        try {
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(passWorld);
            return JWT.create().withClaim("username", userName).withExpiresAt(date).sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}
