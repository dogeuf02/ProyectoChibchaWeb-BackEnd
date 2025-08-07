package com.debloopers.chibchaweb.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SecurityProperties {

    public static String SECRET_KEY;

    @Value("${encrypt.secret-key}")
    public void setSecretKey(String key) {
        SECRET_KEY = key;
    }
}