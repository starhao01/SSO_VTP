package com.example.SSO_APP_VTP.base;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BaseResponse implements Serializable {
    boolean error;
    String message;
    Object data;
}
