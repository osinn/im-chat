package com.gitee.osinn.im.common;

import com.baomidou.mybatisplus.extension.api.IErrorCode;

/**
 * 通用返回对象
 * Created by macro on 2019/4/19.
 */
public class R<T> {
    private long status;
    private String message;
    private T data;

    protected R() {
    }

    protected R(long status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> R<T> success(T data) {
        return new R<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), data);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     * @param  message 提示信息
     */
    public static <T> R<T> success(T data, String message) {
        return new R<T>(ResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 成功返回结果
     */
    public static <T> R<T> success() {
        return new R<T>(ResultCode.SUCCESS.getCode(), null, null);
    }

    /**
     * 成功返回结果
     */
    public static <T> R<T> success(String message) {
        return new R<T>(ResultCode.SUCCESS.getCode(), message, null);
    }

    /**
     * 失败返回结果
     * @param errorCode 错误码
     */
    public static <T> R<T> failed(IErrorCode errorCode) {
        return new R<T>(errorCode.getCode(), errorCode.getMsg(), null);
    }

    /**
     * 失败返回结果
     * @param errorCode 错误码
     * @param message 错误信息
     */
    public static <T> R<T> failed(IErrorCode errorCode, String message) {
        return new R<T>(errorCode.getCode(), message, null);
    }

    /**
     * 失败返回结果
     * @param message 提示信息
     */
    public static <T> R<T> failed(String message) {
        return new R<T>(ResultCode.FAILED.getCode(), message, null);
    }

    /**
     * 失败返回结果
     */
    public static <T> R<T> failed() {
        return failed(ResultCode.FAILED);
    }

    /**
     * 失败返回结果
     * @param data 错误结果
     */
    public static <T> R<T> failed(T data, String message) {
        return new R<T>(ResultCode.FAILED.getCode(), message, data);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> R<T> validateFailed() {
        return failed(ResultCode.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     * @param message 提示信息
     */
    public static <T> R<T> validateFailed(String message) {
        return new R<T>(ResultCode.VALIDATE_FAILED.getCode(), message, null);
    }

    /**
     * 未登录返回结果
     */
    public static <T> R<T> unauthorized(T data) {
        return new R<T>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMsg(), data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> R<T> forbidden(T data) {
        return new R<T>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMsg(), data);
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public String getMsg() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
