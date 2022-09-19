package com.example.SSO_APP_VTP.model.user.dto;

import com.example.SSO_APP_VTP.entity.UserData;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTOResponse {
    boolean error;
    String message;
    String token;
    UserData data;
}
