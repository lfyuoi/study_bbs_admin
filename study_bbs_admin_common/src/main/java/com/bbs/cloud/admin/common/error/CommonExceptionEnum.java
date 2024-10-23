package com.bbs.cloud.admin.common.error;

public enum CommonExceptionEnum implements ExceptionCode {

    SUCCESS(200, "SUCCESS", "OK"),

    FAIL(500, "FAIL", "服务异常"),

    BAD_REQUEST(400, "BAD_REQUEST", "参数异常"),

    TOKEN_NOT_NULL(403, "TOKEN_NOT_NULL", "请输入令牌")

    ;

    private Integer code;

    private String name;

    private String message;

    private CommonExceptionEnum(Integer code, String name, String message) {
        this.code = code;
        this.name = name;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
