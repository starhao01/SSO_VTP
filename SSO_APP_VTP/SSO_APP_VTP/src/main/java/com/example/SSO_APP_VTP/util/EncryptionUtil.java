package com.example.SSO_APP_VTP.util;


import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import java.util.Random;

public class EncryptionUtil {
    static Logger logger = LoggerFactory.getLogger(EncryptionUtil.class.getName());
    // some random salt
    private static final byte[] SALT = {(byte) 0x21, (byte) 0x21, (byte) 0xF0, (byte) 0x55, (byte) 0xC3, (byte) 0x9F, (byte) 0x5A, (byte) 0x75};

    private final static int ITERATION_COUNT = 31;

    private EncryptionUtil() {
    }

    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    public static String encode(String input) {
        if (input == null) {
            throw new IllegalArgumentException();
        }
        try {
            KeySpec keySpec = new PBEKeySpec(null, SALT, ITERATION_COUNT);
            AlgorithmParameterSpec paramSpec = new PBEParameterSpec(SALT, ITERATION_COUNT);

            SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);

            Cipher ecipher = Cipher.getInstance(key.getAlgorithm());
            ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);

            byte[] enc = ecipher.doFinal(input.getBytes());

            String res = new String(Base64.encodeBase64(enc));
            // escapes for url
            res = res.replace('+', '-').replace('/', '_').replace("%", "%25").replace("\n", "%0A");

            return res;

        } catch (Exception e) {
        }

        return "";

    }

    public static String decode(String token) {
        if (token == null) {
            return null;
        }
        try {
            String input = token.replace("%0A", "\n").replace("%25", "%").replace('_', '/').replace('-', '+');
            byte[] dec = Base64.decodeBase64(input.getBytes());
            KeySpec keySpec = new PBEKeySpec(null, SALT, ITERATION_COUNT);
            AlgorithmParameterSpec paramSpec = new PBEParameterSpec(SALT, ITERATION_COUNT);
            SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
            Cipher dcipher = Cipher.getInstance(key.getAlgorithm());
            dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
            byte[] decoded = dcipher.doFinal(dec);
            String result = new String(decoded);
            return result;
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public static String sha256Encode(String password, String salt) {
        String generatedPassword = null;
        try {
            if (!Utils.isNullOrEmpty(password) && !Utils.isNullOrEmpty(salt)) {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                md.update(salt.getBytes(StandardCharsets.UTF_8));
                byte[] bytes = md.digest(password.getBytes());
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < bytes.length; i++) {
                    sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
                }
                generatedPassword = sb.toString();
            }
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }
        return generatedPassword;
    }

    public static String salt() {
        byte[] array = new byte[64];
        new Random().nextBytes(array);
        return Base64.encodeBase64String(array);
    }

    public static void main(String[] args) {
        String salt = salt();
        System.out.println(salt);
        System.out.println(sha256Encode("123456aA@", salt));
    }
}
