package com.szy.demos.mode;

/**
 * Created by Administrator on 2017/6/8.
 */

public class Error {
    public int code;
    public String errorMsg;

    public Error() {
    }

    public Error(int code, String errorMsg) {
        this.code = code;
        this.errorMsg = errorMsg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
