package com.example.SSO_APP_VTP.model.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTOLoginOTPRequest {
    private String username;
    private String otp;
}
