package com.example.SSO_APP_VTP.exception;

import com.example.SSO_APP_VTP.util.Constants;


public class VtException extends Exception{
    int code;
    String message;
    Object data;

    public VtException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public VtException(Constants.STATUS a, String msg) {
        this.code = a.getCode();
        this.message = msg;
    }

    public VtException(String message) {
        this.code = 500;
        this.message = message;
    }

    public boolean verifyError(String errorCode) {
        return code > 0;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getLocalizedMessage() {
        return message;
    }
}
