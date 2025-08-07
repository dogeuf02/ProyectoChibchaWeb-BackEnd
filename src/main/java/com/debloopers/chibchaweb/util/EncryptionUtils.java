package com.debloopers.chibchaweb.util;

import com.debloopers.chibchaweb.security.SecurityProperties;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class EncryptionUtils {

    private static final String ALGORITHM = "AES";

    public static String encrypt(String data) {
        if (SecurityProperties.SECRET_KEY == null) {
            throw new IllegalStateException("SECRET_KEY has not yet been initialized by Spring.");
        }
        try {
            SecretKeySpec key = new SecretKeySpec(SecurityProperties.SECRET_KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encrypted = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("Error while encrypting", e);
        }
    }

    public static String decrypt(String encryptedData) {
        if (SecurityProperties.SECRET_KEY == null) {
            throw new IllegalStateException("SECRET_KEY has not yet been initialized by Spring.");
        }
        try {
            SecretKeySpec key = new SecretKeySpec(SecurityProperties.SECRET_KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decoded = Base64.getDecoder().decode(encryptedData);
            byte[] original = cipher.doFinal(decoded);
            return new String(original);
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting.", e);
        }
    }

    public static String maskCard(String decryptedNumber) {
        if (decryptedNumber == null || decryptedNumber.length() < 4) {
            return "****";
        }
        int length = decryptedNumber.length();
        return "*".repeat(length - 4) + decryptedNumber.substring(length - 4);
    }
}