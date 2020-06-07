package com.jason.web.pojo.response;

import java.io.Serializable;

/**
 * @author: zhangjianhua
 * @date: 2019-12-07 15:45
 **/
public class AopResponse implements Serializable {

    private static final long serialVersionUID = 5075693601892304838L;

    private String code = SUCCESS;

    private String message;

    public AopResponse() {
    }

    public AopResponse(String code) {
        this.code = code;
    }

    public AopResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public boolean success() {
        return SUCCESS.equals(code);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public static final String SUCCESS = "0";

    public static final String PARAM_ERROR = "17";

    public static final String AUTH_ERROR = "18"; //授权错误

    public static final String SYS_ERROR = "20";
}
