package com.wz.yisitest.config;

/**
 * @author：wzxgd
 * @date：2019/10/30 --------------
 */
public class UnauthorizedException extends RuntimeException  {
    public UnauthorizedException() {
        super();
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
