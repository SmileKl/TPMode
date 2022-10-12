/**
 * Copyright 2012 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 * <p>
 * File generated at: 2015年1月20日下午4:57:31
 */
package com.example.vegetables.result;

import java.io.Serializable;

public class ResultVO<T> implements Serializable {

    private static final long serialVersionUID = 5117636053371320760L;

    private Integer errCode;

    private String errMsg;

    private Boolean success;

    private T data;

    private String errName;

    private boolean wrapped;


    public ResultVO() {
        this.wrapped = true;
        this.errCode = 0;
        this.errMsg = "";
        this.success = true;
    }

    public ResultVO(T data) {
        this.wrapped = true;
        this.errCode = 0;
        this.errMsg = "";
        this.data = data;
        this.success = true;
    }

    public ResultVO(T data, boolean wrapped) {
        super();
        this.wrapped = wrapped;
        this.data = data;
        this.errCode = 0;
        this.errMsg = "";
        this.success = true;
    }

    public ResultVO(boolean wrapped, String message, Integer errorCode) {
        super();
        this.wrapped = wrapped;
        this.errCode = errorCode;
        this.errMsg = message;
        this.success = wrapped;
    }


    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrName() {
        return errName;
    }

    public void setErrName(String errName) {
        this.errName = errName;
    }

    public boolean isWrapped() {
        return wrapped;
    }

    public void setWrapped(boolean wrapped) {
        this.wrapped = wrapped;
    }

    public static <T> ResultVO<T> newResponseResult() {
        return new ResultVO<T>();
    }

    /**
     * @return the success
     */
    public Boolean isSuccess() {
        return success;
    }

    /**
     * @param success the success to set
     */
    public void setSuccess(Boolean success) {
        this.success = success;
    }


    public static <T> ResultVO<T> wrapErrorResponse(String msg) {
        return new ResultVO(false, msg, 1);
    }

    public static <T> ResultVO<T> wrapSuccessResponse() {
        return new ResultVO();
    }

    public static <T> ResultVO<T> wrapSuccessResponse(T data) {
        return new ResultVO(data, true);
    }

    public static <T> ResultVO<T> build(T data){
        return new ResultVO(data);
    }
}
