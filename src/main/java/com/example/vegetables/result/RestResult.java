package com.example.vegetables.result;

import com.example.vegetables.common.AbstractRestConstants;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author lifeixiang
 * @description 返回结果封装类
 * @date 2021-12-28
 */
public class RestResult<T> implements Serializable {

    private static final long serialVersionUID = 4487841105935356483L;

    private Integer code;

    private String message;

    private T data;

    /**
     * 判断响应是否成功
     *
     * @return
     */
    public boolean success() {
        return this.code != null && this.code.equals(AbstractRestConstants.RESPONSE_CODE_SUCCESS);
    }

    public boolean notSuccess() {
        return !success();
    }

    /**
     * 判断data结果是否为null
     *
     * @return
     */
    public boolean dataNotNull() {
        return this.data != null;
    }

    public boolean dataIsNull() {
        return !dataNotNull();
    }

    public boolean dataIsEmpty() {
        return !dataNotEmpty();
    }

    /**
     * 判断data是否为Empty(data是Collection或Map的子类才能调用本方法,否则抛非法参数异常)
     *
     * @return
     */
    public boolean dataNotEmpty() {
        if (!(data instanceof Collection) && !(this.data instanceof Map)) {
            throw new IllegalArgumentException("判断data是否empty的api,只支持data类型为Collections或Map");
        }

        if (data instanceof List) {
            return CollectionUtils.isNotEmpty((Collection) data);
        }

        if (data instanceof Map) {
            return MapUtils.isNotEmpty((Map) data);
        }

        return false;
    }

    public RestResult() {

    }

    public RestResult(Integer code) {
        this.code = code;
    }

    public RestResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public RestResult(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public RestResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Integer getCode() {
        return code == null ? 0 : code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message == null ? "" : message;
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

    public static <T> RestResult<T> wrapResponse(Integer code, String msg, T data) {
        return new RestResult<T>(code, msg, data);
    }

    public static <T> RestResult<T> wrapResponse(Integer code, T data) {
        return new RestResult<T>(code,data);
    }

    public static <T> RestResult<T> wrapResponse(Integer code, String msg) {
        return new RestResult<T>(code, msg);
    }

    public static <T> RestResult<T> wrapSuccessResponse() {
        return new RestResult<T>(AbstractRestConstants.RESPONSE_CODE_SUCCESS);
    }

    public static <T> RestResult<T> wrapSuccessResponse(T data) {
        return new RestResult<T>(AbstractRestConstants.RESPONSE_CODE_SUCCESS, data);
    }

    public static <T> RestResult<T> wrapErrorResponse(String msg) {
        return new RestResult<T>(AbstractRestConstants.RESPONSE_CODE_FAILED, msg);
    }

    public static <T> RestResult<T> wrapErrorResponse(String msg,T data) {
        return new RestResult<T>(AbstractRestConstants.RESPONSE_CODE_FAILED, msg,data);
    }

    public static <T> RestResult<T> wrapErrorResponse(Integer code, String msg) {
        return new RestResult<T>(code, msg);
    }

}