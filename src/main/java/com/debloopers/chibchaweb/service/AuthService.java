package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.dto.ResponseDTO;
import com.debloopers.chibchaweb.entity.Usuario;
import com.debloopers.chibchaweb.dto.LoginRequestDTO;
import com.debloopers.chibchaweb.dto.LoginResponseDTO;
import com.debloopers.chibchaweb.repository.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;

    private final TokenVerificacionService tokenVerificacionService;

    private final TokenListaNegraService tokenListaNegraService;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final CaptchaService captchaService;

    public AuthService(final UsuarioRepository usuarioRepository,
                       final TokenVerificacionService tokenVerificacionService,
                       final PasswordEncoder passwordEncoder,
                       final TokenListaNegraService tokenListaNegraService,
                       final JwtService jwtService,
                       final CaptchaService captchaService) {
        this.usuarioRepository = usuarioRepository;
        this.tokenVerificacionService = tokenVerificacionService;
        this.passwordEncoder = passwordEncoder;
        this.tokenListaNegraService = tokenListaNegraService;
        this.jwtService = jwtService;
        this.captchaService = captchaService;
    }


    public LoginResponseDTO login(LoginRequestDTO dto) {

        boolean captchaOk;
        try {
            captchaOk = captchaService.verifyCaptcha(dto.getCaptchaToken());
        } catch (Exception recaptchaEx) {
            return new LoginResponseDTO(false, "The captcha could not be verified. Please try again later.",null);
        }

        if (!captchaOk) {
            return new LoginResponseDTO(false, "Invalid captcha. Please try again.",null);
        }

        Usuario usuario = usuarioRepository.findByCorreoUsuario(dto.getCorreo());

        if (usuario == null) {
            return new LoginResponseDTO(false, "Unregistered email", null);
        }

        if ("INACTIVO".equalsIgnoreCase(usuario.getEstado())) {
            return new LoginResponseDTO(false, "The user is inactive.", null);
        }

        if ("PENDIENTE".equalsIgnoreCase(usuario.getEstado())) {
            return new LoginResponseDTO(false, "Account pending from approval.", null);
        }

        boolean coincide = passwordEncoder.matches(dto.getContrasena(), usuario.getContrasena());
        if (!coincide) {
            return new LoginResponseDTO(false, "Incorrect password", null);
        }

        String token = jwtService.generateToken(usuario);

        return new LoginResponseDTO(
                true,
                "Login successful",
                token
        );
    }

    public void logoutUsuario(HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("The Bearer token was not found in the header.");
        }

        final String token = authHeader.substring(7);
        tokenListaNegraService.invalidarToken(token);
    }

    public ResponseEntity<String> activarCuentaConToken(String token) {
        try {
            Usuario usuario = tokenVerificacionService.validarToken(token);
            usuario.setEstado("ACTIVO");
            usuarioRepository.save(usuario);

            return ResponseEntity.ok("Account successfully activated.");
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}