package com.example.SSO_APP_VTP.base;


import com.example.SSO_APP_VTP.entity.UserData;
import com.example.SSO_APP_VTP.util.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

public class BaseController {
    protected UserData getCurrentUser() throws Exception {
        UserData info = null;
        try {
            info = (UserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
        }
        if (info == null || info.getId() == null) {
            throw new Exception();
        }
        return info;
    }

    protected ResponseEntity<BaseResponse> successApi(Object data, String message) {
        return ResponseEntity.ok(new BaseResponse(false, message, data));
    }

    protected ResponseEntity<BaseResponse> customOutput(Boolean error, Object data, String message) {
        return ResponseEntity.ok(new BaseResponse(error, message, data));
    }

    protected ResponseEntity<BaseResponse> errorApi(String message) {
        return ResponseEntity.ok(new BaseResponse(true, message, null));
    }

    protected ResponseEntity<BaseResponse> errorWithDataApi(Object data, String message) {
        return ResponseEntity.ok(new BaseResponse(true, message, data));
    }

    public ResponseEntity resultOk(String message, Object body) {
        return new ResponseEntity(new BaseResponse(false, Utils.isNullOrEmpty(message) ? "OK" : message, body), HttpStatus.OK);
    }
}
