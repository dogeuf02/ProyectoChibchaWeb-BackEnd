package com.debloopers.chibchaweb.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProperties {

    public static String SECRET_KEY;

    @Value("${jwt.secret-key}")
    public void setSecretKey(String key) {
        SECRET_KEY = key;
    }
}

