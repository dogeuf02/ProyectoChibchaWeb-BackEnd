package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.domain.Usuario;
import com.debloopers.chibchaweb.model.LoginRequestDTO;
import com.debloopers.chibchaweb.model.LoginResponseDTO;
import com.debloopers.chibchaweb.repos.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginResponseDTO login(LoginRequestDTO dto) {
        Usuario usuario = usuarioRepository.findByCorreoUsuario(dto.getCorreo());
        if (usuario == null) {
            return new LoginResponseDTO(false, "Unregistered email", null, null);
        }

        if ("INACTIVO".equalsIgnoreCase(usuario.getEstado())) {
            return new LoginResponseDTO(false, "The user is inactive.", null, null);
        }

        if ("PENDIENTE".equalsIgnoreCase(usuario.getEstado())) {
            return new LoginResponseDTO(false, "The user has not yet been activated.", null, null);
        }

        boolean coincide = passwordEncoder.matches(dto.getContrasena(), usuario.getContrasena());
        if (!coincide) {
            return new LoginResponseDTO(false, "Incorrect password", null, null);
        }

        return new LoginResponseDTO(true, "Login successful", usuario.getRol(), usuario.getIdUsuario());
    }
}