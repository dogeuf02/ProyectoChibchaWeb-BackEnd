package com.debloopers.chibchaweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/redis")
public class RedisTestController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @PreAuthorize("hasAuthority('Administrador')")
    @GetMapping("/ping")
    public String ping() {
        redisTemplate.opsForValue().set("test-key", "Hello Redis", Duration.ofMinutes(1));
        return redisTemplate.opsForValue().get("test-key");
    }
}