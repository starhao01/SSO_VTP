package com.example.SSO_APP_VTP.serrvice;

import com.example.SSO_APP_VTP.entity.UserData;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface UserService {
    Map<String, Object> getUserData(String username, String mabuuCuc) throws Exception;
}
