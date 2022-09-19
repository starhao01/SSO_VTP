package com.example.SSO_APP_VTP.entity;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserData implements Serializable {
    Long id;
    Long cusID;
    Long cusRole;
    String email;
    String phone;
    String displayName;
    Date birtDate;
    String sex;
    Long postID;
    Long partnerID;
    String partnerCode;
    String partnerName;
    Long provinceID;
    Long districtID;
    Long wardsID;
    String address;
    String active;
    String tokenKey;
    String userName;
    String password;
    List<ModuleData> lsModule;
    Long parentStatus;
    int childCount;
    List<Integer> extraRoles;
    String apiToken;
    String evtpUser;
    Long evtpId;
    Long parentEvtpId;
    String parentCode;
    String accountCode;
    String evtpCode;
    String idNumber;
    Long userGroupId;
    String settingPath;
    String code;
    String balance;
    Long rewards;
    Long status;
    Date idDate;
    String listRole;
    Date createdDate;
    Date lastLoginDate;
    String loginHis;

    public String getEmail() {
        if (email == null) {
            email = "";
        }
        return email;
    }

    public List<Integer> getExtraRoles() {
        if (extraRoles == null) {
            extraRoles = new ArrayList<>();
        }
        return extraRoles;
    }


    public boolean hasRole(int role) {
        List<Integer> ll = getExtraRoles();
        for (int i : ll) {
            if (i == role) {
                return true;
            }
        }
        return false;
    }


}
