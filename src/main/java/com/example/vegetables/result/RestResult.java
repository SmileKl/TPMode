//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.vegetables.result;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.example.vegetables.common.AbstractRestConstants;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

public class RestResult<T> implements Serializable {
    private static final long serialVersionUID = 4487841105935356483L;
    private Integer code;
    private String message;
    private T data;

    public boolean success() {
        return this.code != null && this.code.equals(AbstractRestConstants.RESPONSE_CODE_SUCCESS);
    }

    public boolean notSuccess() {
        return !this.success();
    }

    public boolean dataNotNull() {
        return this.data != null;
    }

    public boolean dataIsNull() {
        return !this.dataNotNull();
    }

    public boolean dataIsEmpty() {
        return !this.dataNotEmpty();
    }

    public boolean dataNotEmpty() {
        if (!(this.data instanceof Collection) && !(this.data instanceof Map)) {
            throw new IllegalArgumentException("判断data是否empty的api,只支持data类型为Collections或Map");
        } else if (this.data instanceof List) {
            return CollectionUtils.isNotEmpty((Collection)this.data);
        } else {
            return this.data instanceof Map ? MapUtils.isNotEmpty((Map)this.data) : false;
        }
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
        return this.code == null ? 0 : this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message == null ? "" : this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> RestResult<T> wrapResponse(Integer code, String msg, T data) {
        return new RestResult(code, msg, data);
    }

    public static <T> RestResult<T> wrapResponse(Integer code, T data) {
        return new RestResult(code, data);
    }

    public static <T> RestResult<T> wrapResponse(Integer code, String msg) {
        return new RestResult(code, msg);
    }

    public static <T> RestResult<T> wrapSuccessResponse() {
        return new RestResult(AbstractRestConstants.RESPONSE_CODE_SUCCESS);
    }

    public static <T> RestResult<T> wrapSuccessResponse(T data) {
        return new RestResult(AbstractRestConstants.RESPONSE_CODE_SUCCESS, data);
    }

    public static <T> RestResult<T> wrapErrorResponse(String msg) {
        return new RestResult(AbstractRestConstants.RESPONSE_CODE_FAILED, msg);
    }

    public static <T> RestResult<T> wrapErrorResponse(String msg, T data) {
        return new RestResult(AbstractRestConstants.RESPONSE_CODE_FAILED, msg, data);
    }

    public static <T> RestResult<T> wrapErrorResponse(Integer code, String msg) {
        return new RestResult(code, msg);
    }
}
