//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.vegetables.common;

import java.util.HashMap;
import java.util.Map;

public class AbstractRestConstants {
    public static final Integer RESPONSE_CODE_SUCCESS = 1;
    public static final Integer RESPONSE_CODE_FAILED = 0;
    public static final Integer ERROR_CODE_SYSTEM_ERROR = 10001;
    public static final Integer ERROR_CODE_PARAM_ERROR = 10003;
    public static final Integer ERROR_CODE_TOKEN_LOGOUT = 10004;
    public static final Integer ERROR_CODE_TOKEN_EXPIRE = 10006;
    public static final Integer ERROR_CODE_TOKEN_FAIL = 10007;
    public static final Integer ERROR_CODE_NOT_LOGIN = 10008;
    public static final Integer ERROR_CODE_REPEAT_COMMIT = 10009;
    public static final Integer ERROR_CODE_IMPORT_ERROR = 10010;
    public static final Integer ERROR_CODE_GET_USER_FAIL = 10011;
    public static final Integer ERROR_CODE_CONVENTION_ADD_FAIL = 10100;
    public static final Integer ERROR_CODE_CONVENTION_RESIDENT_AUDITING = 10101;
    public static final Integer ERROR_CODE_NOTASK_FAIL = 11101;
    public static final Integer ERROR_CODE_EVENT_ERROR = 12000;
    public static Map<Integer, String> commonErrorCodeMap = new HashMap();

    public AbstractRestConstants() {
    }

    public static void initCommonErrorCodeMap(Map<Integer, String> commonErrorCodeMap) {
        commonErrorCodeMap.put(ERROR_CODE_SYSTEM_ERROR, "系统异常错误");
        commonErrorCodeMap.put(ERROR_CODE_PARAM_ERROR, "参数验证错误");
        commonErrorCodeMap.put(ERROR_CODE_TOKEN_LOGOUT, "登录失效,请重新登录");
        commonErrorCodeMap.put(ERROR_CODE_TOKEN_EXPIRE, "登录过期,请重新登录");
        commonErrorCodeMap.put(ERROR_CODE_TOKEN_FAIL, "身份验证失败");
        commonErrorCodeMap.put(ERROR_CODE_NOT_LOGIN, "请先登录");
        commonErrorCodeMap.put(ERROR_CODE_REPEAT_COMMIT, "重复提交!");
        commonErrorCodeMap.put(ERROR_CODE_GET_USER_FAIL, "换取用户失败");
    }

    public static String getMessage(Integer errorCode) {
        return (String)commonErrorCodeMap.get(errorCode);
    }

    static {
        initCommonErrorCodeMap(commonErrorCodeMap);
    }
}
