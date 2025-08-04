package com.debloopers.chibchaweb.controller;

import com.debloopers.chibchaweb.dto.LoginRequestDTO;
import com.debloopers.chibchaweb.dto.LoginResponseDTO;
import com.debloopers.chibchaweb.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody @Valid LoginRequestDTO dto) {
        LoginResponseDTO respuesta = authService.login(dto);
        if (!respuesta.isAutenticado()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(respuesta);
        }
        return ResponseEntity.ok(respuesta);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        try {
            authService.logoutUsuario(request);
            return ResponseEntity.ok("Session closed successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/activar")
    public ResponseEntity<String> activarCuenta(@RequestParam("token") String token) {
        return authService.activarCuentaConToken(token);
    }
}