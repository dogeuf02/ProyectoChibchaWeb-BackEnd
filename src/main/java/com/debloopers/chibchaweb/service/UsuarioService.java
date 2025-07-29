package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.entity.Administrador;
import com.debloopers.chibchaweb.entity.ClienteDirecto;
import com.debloopers.chibchaweb.entity.Distribuidor;
import com.debloopers.chibchaweb.entity.Empleado;
import com.debloopers.chibchaweb.entity.Usuario;
import com.debloopers.chibchaweb.dto.UsuarioActualizarDTO;
import com.debloopers.chibchaweb.dto.UsuarioDTO;
import com.debloopers.chibchaweb.repository.AdministradorRepository;
import com.debloopers.chibchaweb.repository.ClienteDirectoRepository;
import com.debloopers.chibchaweb.repository.DistribuidorRepository;
import com.debloopers.chibchaweb.repository.EmpleadoRepository;
import com.debloopers.chibchaweb.repository.UsuarioRepository;
import com.debloopers.chibchaweb.util.NotFoundException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ClienteDirectoRepository clienteDirectoRepository;
    private final AdministradorRepository administradorRepository;
    private final EmpleadoRepository empleadoRepository;
    private final DistribuidorRepository distribuidorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioService(final UsuarioRepository usuarioRepository,
            final ClienteDirectoRepository clienteDirectoRepository,
            final AdministradorRepository administradorRepository,
            final EmpleadoRepository empleadoRepository,
            final DistribuidorRepository distribuidorRepository) {
        this.usuarioRepository = usuarioRepository;
        this.clienteDirectoRepository = clienteDirectoRepository;
        this.administradorRepository = administradorRepository;
        this.empleadoRepository = empleadoRepository;
        this.distribuidorRepository = distribuidorRepository;
    }

    public List<UsuarioDTO> findAll() {
        final List<Usuario> usuarios = usuarioRepository.findAll(Sort.by("idUsuario"));
        return usuarios.stream()
                .map(usuario -> mapToDTO(usuario, new UsuarioDTO()))
                .toList();
    }

    public UsuarioDTO get(final Integer idUsuario) {
        return usuarioRepository.findById(idUsuario)
                .map(usuario -> mapToDTO(usuario, new UsuarioDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final UsuarioDTO usuarioDTO) {
        final Usuario usuario = new Usuario();
        mapToEntity(usuarioDTO, usuario);
        return usuarioRepository.save(usuario).getIdUsuario();
    }

    public void update(final String correoUsuario, final UsuarioActualizarDTO usuarioDTO) {
        final Usuario usuario = usuarioRepository.findByCorreoUsuario(correoUsuario);

        if (usuario == null) {
            throw new NotFoundException("User with email address not found: " + correoUsuario);
        }

        if (usuarioDTO.getContrasena() != null && !usuarioDTO.getContrasena().isBlank()) {
            usuario.setContrasena(passwordEncoder.encode(usuarioDTO.getContrasena().trim()));
        }

        if (usuarioDTO.getEstado() != null && !usuarioDTO.getEstado().isBlank()) {
            List<String> estadosValidos = List.of("ACTIVO", "INACTIVO", "PENDIENTE");
            String estadoNormalizado = usuarioDTO.getEstado().trim().toUpperCase();
            if (estadosValidos.contains(estadoNormalizado)) {
                usuario.setEstado(estadoNormalizado);
            } else {
                throw new IllegalArgumentException("Invalid status: " + usuarioDTO.getEstado());
            }
        }

        usuarioRepository.save(usuario);
    }

    public void updateById(final Integer idUsuario, final UsuarioActualizarDTO usuarioDTO) {
        final Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new NotFoundException("Usuario con ID no encontrado: " + idUsuario));

        if (usuarioDTO.getContrasena() != null && !usuarioDTO.getContrasena().isBlank()) {
            usuario.setContrasena(passwordEncoder.encode(usuarioDTO.getContrasena().trim()));
        }

        if (usuarioDTO.getEstado() != null && !usuarioDTO.getEstado().isBlank()) {
            List<String> estadosValidos = List.of("ACTIVO", "INACTIVO", "PENDIENTE");
            String estadoNormalizado = usuarioDTO.getEstado().trim().toUpperCase();
            if (estadosValidos.contains(estadoNormalizado)) {
                usuario.setEstado(estadoNormalizado);
            } else {
                throw new IllegalArgumentException("Estado inválido: " + usuarioDTO.getEstado());
            }
        }
        usuarioRepository.save(usuario);
    }

    public void delete(final Integer idUsuario) {
        usuarioRepository.deleteById(idUsuario);
    }

    private UsuarioDTO mapToDTO(final Usuario usuario, final UsuarioDTO usuarioDTO) {
        usuarioDTO.setIdUsuario(usuario.getIdUsuario());
        usuarioDTO.setCorreoUsuario(usuario.getCorreoUsuario());
        usuarioDTO.setContrasena(usuario.getContrasena());
        usuarioDTO.setRol(usuario.getRol());
        usuarioDTO.setEstado(usuario.getEstado());
        usuarioDTO.setCliente(usuario.getCliente() == null ? null : usuario.getCliente().getIdCliente());
        usuarioDTO.setAdmin(usuario.getAdmin() == null ? null : usuario.getAdmin().getIdAdmin());
        usuarioDTO.setEmpleado(usuario.getEmpleado() == null ? null : usuario.getEmpleado().getIdEmpleado());
        usuarioDTO.setDistribuidor(usuario.getDistribuidor() == null ? null : usuario.getDistribuidor().getIdDistribuidor());
        return usuarioDTO;
    }

    private Usuario mapToEntity(final UsuarioDTO usuarioDTO, final Usuario usuario) {
        usuario.setCorreoUsuario(usuarioDTO.getCorreoUsuario());
        usuario.setContrasena(usuarioDTO.getContrasena());
        usuario.setRol(usuarioDTO.getRol());
        usuario.setEstado(usuarioDTO.getEstado());
        final ClienteDirecto cliente = usuarioDTO.getCliente() == null ? null : clienteDirectoRepository.findById(usuarioDTO.getCliente())
                .orElseThrow(() -> new NotFoundException("cliente not found"));
        usuario.setCliente(cliente);
        final Administrador admin = usuarioDTO.getAdmin() == null ? null : administradorRepository.findById(usuarioDTO.getAdmin())
                .orElseThrow(() -> new NotFoundException("admin not found"));
        usuario.setAdmin(admin);
        final Empleado empleado = usuarioDTO.getEmpleado() == null ? null : empleadoRepository.findById(usuarioDTO.getEmpleado())
                .orElseThrow(() -> new NotFoundException("empleado not found"));
        usuario.setEmpleado(empleado);
        final Distribuidor distribuidor = usuarioDTO.getDistribuidor() == null ? null : distribuidorRepository.findById(usuarioDTO.getDistribuidor())
                .orElseThrow(() -> new NotFoundException("distribuidor not found"));
        usuario.setDistribuidor(distribuidor);
        return usuario;
    }

}
