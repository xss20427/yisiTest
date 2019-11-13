package com.wz.yisitest.Response;

public enum ResultEnums {

    SUCCESS("200", "请求成功"),

    ERROR("000", "请求失败"),

    SYSTEM_ERROR("100", "系统异常"),

    BUSSINESS_ERROR("201", "业务逻辑错误"),

    VERIFY_CODE_ERROR("202", "业务参数错误"),

    PARAM_ERROR("203", "业务参数错误");

    private String code;
    private String msg;

    ResultEnums(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
