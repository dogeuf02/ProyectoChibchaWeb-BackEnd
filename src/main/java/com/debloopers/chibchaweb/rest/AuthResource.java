package com.debloopers.chibchaweb.rest;

import com.debloopers.chibchaweb.model.LoginRequestDTO;
import com.debloopers.chibchaweb.model.LoginResponseDTO;
import com.debloopers.chibchaweb.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthResource {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto) {
        LoginResponseDTO respuesta = authService.login(dto);
        if (!respuesta.isAutenticado()) {
            return ResponseEntity.status(401).body(respuesta);
        }
        return ResponseEntity.ok(respuesta);
    }
}

