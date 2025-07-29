package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.entity.Usuario;
import com.debloopers.chibchaweb.dto.LoginRequestDTO;
import com.debloopers.chibchaweb.dto.LoginResponseDTO;
import com.debloopers.chibchaweb.repository.UsuarioRepository;
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
            return new LoginResponseDTO(false, "Unregistered email", null, null, null);
        }

        if ("INACTIVO".equalsIgnoreCase(usuario.getEstado())) {
            return new LoginResponseDTO(false, "The user is inactive.", null, null, null);
        }

        if ("PENDIENTE".equalsIgnoreCase(usuario.getEstado())) {
            return new LoginResponseDTO(false, "Account pending from approval.", null, null, null);
        }

        boolean coincide = passwordEncoder.matches(dto.getContrasena(), usuario.getContrasena());
        if (!coincide) {
            return new LoginResponseDTO(false, "Incorrect password", null, null, null);
        }

        Integer idRelacionado = null;
        if (usuario.getAdmin() != null) {
            idRelacionado = usuario.getAdmin().getIdAdmin();
        } else if (usuario.getCliente() != null) {
            idRelacionado = usuario.getCliente().getIdCliente();
        } else if (usuario.getEmpleado() != null) {
            idRelacionado = usuario.getEmpleado().getIdEmpleado();
        } else if (usuario.getDistribuidor() != null) {
            idRelacionado = usuario.getDistribuidor().getIdDistribuidor();
        }

        return new LoginResponseDTO(true, "Login successful", usuario.getRol(), usuario.getIdUsuario(),idRelacionado );
    }
}