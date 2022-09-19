package com.example.SSO_APP_VTP.model.user.mapper;

import com.example.SSO_APP_VTP.entity.UserData;
import com.example.SSO_APP_VTP.model.user.dto.UserDTOResponse;

public class UserMapper {
    public static UserDTOResponse toUserDTOResponse(UserData userData){
        userData.setPassword("");
        return UserDTOResponse.builder().data(userData).error("null").message("successfully").build();
    }

}
