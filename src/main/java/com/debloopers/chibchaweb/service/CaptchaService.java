package com.debloopers.chibchaweb.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import java.util.Map;

@Service
public class CaptchaService {

    @Value("${google.recaptcha.secret}")
    private String recaptchaSecret;

    private final RestTemplate restTemplate;

    private final String VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    public CaptchaService(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean verifyCaptcha(String token) {

        String params = "?secret=" + recaptchaSecret + "&response=" + token;
        ResponseEntity<Map> response = restTemplate.postForEntity(VERIFY_URL + params, null, Map.class);

        Map body = response.getBody();
        return (Boolean) body.get("success");
    }
}