package com.healthmanager.common;

/**
 * 返回结果状态码枚举类
 */
public enum ResultCode {
    SUCCESS(0, "成功"),
    FAILED(2001, "服务器内部错误"),
    VALIDATE_FAILED(1001, "参数错误"),
    RESOURCE_NOT_FOUND(1002, "资源不存在"),
    UNAUTHORIZED(3001, "未授权"),
    FORBIDDEN(3002, "禁止访问");

    private final Integer code;
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
} 