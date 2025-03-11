package com.healthmanager.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 统一响应结果类
 * @param <T> 数据类型
 */
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 消息
     */
    private String message;

    /**
     * 数据
     */
    private T data;

    /**
     * 成功结果（无数据）
     * @return 成功结果
     */
    public static <T> Result<T> success() {
        return success(null);
    }

    /**
     * 成功结果（有数据）
     * @param data 数据
     * @return 成功结果
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("操作成功");
        result.setData(data);
        return result;
    }

    /**
     * 失败结果
     * @param code 状态码
     * @param message 消息
     * @return 失败结果
     */
    public static <T> Result<T> error(Integer code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    /**
     * 失败结果（默认状态码500）
     * @param message 消息
     * @return 失败结果
     */
    public static <T> Result<T> error(String message) {
        return error(500, message);
    }
} 