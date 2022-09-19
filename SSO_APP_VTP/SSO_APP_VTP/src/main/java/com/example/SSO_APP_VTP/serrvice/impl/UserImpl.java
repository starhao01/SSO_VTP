package com.example.SSO_APP_VTP.serrvice.impl;

import com.example.SSO_APP_VTP.entity.UserData;
import com.example.SSO_APP_VTP.model.user.dto.UserDTOLoginRequest;
import com.example.SSO_APP_VTP.model.user.dto.UserDTOResponse;
import com.example.SSO_APP_VTP.serrvice.UserService;
import com.example.SSO_APP_VTP.serrvice.daos.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
import java.util.Optional;

@Service
public class UserImpl  implements UserService {
    @Autowired
    UserDAO userDAO;

    @Override
    public Map<String, Object> getUserData(String username, String mabuuCuc) throws Exception {
        return  userDAO.getUserInfo(username,mabuuCuc);
    }

    public Map<String, UserDTOResponse> loginAuthenticate(@RequestBody UserDTOLoginRequest userDTOLoginRequest) throws Exception {
        //Optional userOptional = getUserData(userDTOLoginRequest.getUsername(),userDTOLoginRequest.getPassword());
        boolean isAuthenticated = false;
        return null;
    }
}
