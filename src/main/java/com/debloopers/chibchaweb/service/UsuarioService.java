package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.domain.Usuario;
import com.debloopers.chibchaweb.model.UsuarioDTO;
import com.debloopers.chibchaweb.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ClienteDirectoRepository clienteRepository;

    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private RegistradorRepository registradorRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean create(UsuarioDTO dto) {
        try {
            Usuario usuario = new Usuario();
            usuario.setCorreoUsuario(dto.getCorreoUsuario());
            usuario.setContrasena(passwordEncoder.encode(dto.getContrasena()));
            usuario.setRolUsuario(dto.getRolUsuario());
            usuarioRepository.save(usuario);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public UsuarioDTO findById(Integer id) {
        return usuarioRepository.findById(id)
                .map(this::toDto)
                .orElse(null);
    }

    public boolean updateByCorreo(String correo, UsuarioDTO dto) {
        try {
            Usuario usuario = usuarioRepository.findByCorreoUsuario(correo);
            if (usuario == null) {
                return false;
            }

            if (dto.getContrasena() != null) {
                usuario.setContrasena(passwordEncoder.encode(dto.getContrasena()));
            }
            usuarioRepository.save(usuario);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<UsuarioDTO> findAll() {
        return usuarioRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private UsuarioDTO toDto(Usuario u) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario(u.getIdUsuario());
        dto.setCorreoUsuario(u.getCorreoUsuario());
        dto.setRolUsuario(u.getRolUsuario());
        // contraseña no se devuelve, eliminar esta linea al terminar el desarrollo
        dto.setContrasena(u.getContrasena());

        if (u.getCliente() != null) dto.setIdCliente(u.getCliente().getIdCliente());
        if (u.getAdmin() != null) dto.setIdAdmin(u.getAdmin().getIdAdmin());
        if (u.getEmpleado() != null) dto.setIdEmpleado(u.getEmpleado().getIdEmpleado());
        if (u.getRegistrador() != null) dto.setIdRegistrador(u.getRegistrador().getIdRegistrador());
        return dto;
    }
}