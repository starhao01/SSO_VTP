package com.example.SSO_APP_VTP.controller;

import com.example.SSO_APP_VTP.base.BaseResponse;
import com.example.SSO_APP_VTP.entity.UserData;
import com.example.SSO_APP_VTP.exception.VtException;
import com.example.SSO_APP_VTP.model.user.dto.UserDTOLoginRequest;
import com.example.SSO_APP_VTP.model.user.dto.UserDTOResponse;
import com.example.SSO_APP_VTP.serrvice.UserService;
import com.example.SSO_APP_VTP.util.Constants;
import com.example.SSO_APP_VTP.util.EncryptionUtil;
import com.example.SSO_APP_VTP.util.RsaCrypto;
import com.example.SSO_APP_VTP.util.Utils;
import com.google.gson.JsonObject;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Map;
import java.util.logging.Logger;

import static com.example.SSO_APP_VTP.util.EncryptionUtil.logger;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/testapi")
    public Map<String, Object> testapi() throws Exception {
        return userService.getUserData("nguontest22","TN2");
    }
    @PostMapping("/v2login")
    public UserDTOResponse login(@RequestBody UserDTOLoginRequest request) throws Exception {
        UserDTOResponse userDTOResponse = new UserDTOResponse();
        if (Utils.isNullOrEmpty(request.getUsername())) {
            throw new VtException(Constants.STATUS.E203, "Tài khoản không được để trống");
        }
        if (Utils.isNullOrEmpty(request.getPassword())) {
            throw new VtException(Constants.STATUS.E203, "Mật khẩu không được để trống");
        }
        Map<String, Object> result = userService.getUserData(request.getUsername(), request.getPostcode());
        String salt = (String) result.get(Constants.pwdSaltKey);
        String pwdEncrypted = (String) result.get(Constants.pwdKey);
        String compare = EncryptionUtil.sha256Encode(request.getPassword(), salt);
//            if (result.isEmpty() || compare == null || !compare.equals(pwdEncrypted)) {
//                throw new VtException(Constants.STATUS.E204, "Tài khoản hoặc mật khẩu không hợp lệ");
//            }
        userDTOResponse.setError(true);
        userDTOResponse.setMessage(createJWT(result));
        userDTOResponse.setData((UserData) result);
        return userDTOResponse;
    }

    private String createJWT(Map<String, Object> claim) throws Exception {
        Long exp = System.currentTimeMillis() + 24L * 60 * 60 * 1000;
        claim.remove(Constants.pwdSaltKey);
        claim.remove(Constants.pwdKey);
        claim.put("exp", exp + "");
        claim.put("source", -1);
        JsonObject header = new JsonObject();
        header.addProperty("alg", "RS256");
        header.addProperty("typ", "JWT");
        header.addProperty("exp", exp + "");
        RSAPrivateKey _privateKey = RsaCrypto.LoadPrivateKey2("keys/EvtpPrivate.pem", true);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(_privateKey.getEncoded());
        String retStr = null;
        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PrivateKey privKey = kf.generatePrivate(keySpec);
            retStr = Jwts.builder().setClaims(claim).signWith(SignatureAlgorithm.RS256, privKey).compact();

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return retStr;
    }
}
