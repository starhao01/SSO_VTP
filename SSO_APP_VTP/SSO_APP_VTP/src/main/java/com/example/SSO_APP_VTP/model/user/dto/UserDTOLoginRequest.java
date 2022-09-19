package com.example.SSO_APP_VTP.model.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTOLoginRequest {
    private String username;
    private String password;
    private String postcode;
}
