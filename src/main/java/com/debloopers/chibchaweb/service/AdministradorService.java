package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.entity.Administrador;
import com.debloopers.chibchaweb.entity.SolicitudDominio;
import com.debloopers.chibchaweb.entity.Usuario;
import com.debloopers.chibchaweb.dto.*;
import com.debloopers.chibchaweb.repository.AdministradorRepository;
import com.debloopers.chibchaweb.repository.SolicitudDominioRepository;
import com.debloopers.chibchaweb.repository.UsuarioRepository;
import com.debloopers.chibchaweb.util.NotFoundException;
import com.debloopers.chibchaweb.util.ReferencedWarning;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AdministradorService {

    private final AdministradorRepository administradorRepository;
    private final UsuarioRepository usuarioRepository;
    private final SolicitudDominioRepository solicitudDominioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AdministradorService(final AdministradorRepository administradorRepository,
                                final UsuarioRepository usuarioRepository,
                                final SolicitudDominioRepository solicitudDominioRepository) {
        this.administradorRepository = administradorRepository;
        this.usuarioRepository = usuarioRepository;
        this.solicitudDominioRepository = solicitudDominioRepository;
    }

    public List<AdministradorDTO> findAll() {
        final List<Administrador> administradors = administradorRepository.findAll(Sort.by("idAdmin"));
        return administradors.stream()
                .map(administrador -> mapToDTO(administrador, new AdministradorDTO()))
                .toList();
    }

    public AdministradorDTO get(final Integer idAdmin) {
        return administradorRepository.findById(idAdmin)
                .map(administrador -> mapToDTO(administrador, new AdministradorDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Transactional
    public ResponseDTO create(AdministradorRegistroRequestDTO dto) {
        try {
            if (usuarioRepository.findByCorreoUsuario(dto.getCorreoAdmin()) != null) {
                return new ResponseDTO(false, "The email is already registered.");
            }

            Administrador administrador = new Administrador();
            administrador.setNombreAdmin(dto.getNombreAdmin());
            administrador.setApellidoAdmin(dto.getApellidoAdmin());
            administrador.setFechaNacimientoAdmin(dto.getFechaNacimientoAdmin());
            administradorRepository.save(administrador);

            Usuario usuario = new Usuario();
            usuario.setCorreoUsuario(dto.getCorreoAdmin());
            usuario.setContrasena(passwordEncoder.encode(dto.getContrasenaAdmin()));
            usuario.setRol("Administrador");
            usuario.setEstado("ACTIVO");
            usuario.setAdmin(administrador);
            usuarioRepository.save(usuario);

            return new ResponseDTO(true, "Administrator successfully created.");
        } catch (Exception e) {
            return new ResponseDTO(false, "Internal error when creating the administrator.");
        }
    }

    public void update(final Integer idAdmin, final AdministradorActualizarDTO administradorDTO) {
        final Administrador administrador = administradorRepository.findById(idAdmin)
                .orElseThrow(NotFoundException::new);

        if (administradorDTO.getNombreAdmin() != null && !administradorDTO.getNombreAdmin().isBlank()) {
            administrador.setNombreAdmin(administradorDTO.getNombreAdmin());
        }

        if (administradorDTO.getApellidoAdmin() != null && !administradorDTO.getApellidoAdmin().isBlank()) {
            administrador.setApellidoAdmin(administradorDTO.getApellidoAdmin());
        }

        if (administradorDTO.getFechaNacimientoAdmin() != null) {
            administrador.setFechaNacimientoAdmin(administradorDTO.getFechaNacimientoAdmin());
        }

        administradorRepository.save(administrador);
    }

    public List<AdministradorConCorreoDTO> findAllWithCorreo() {
        List<Administrador> administradores = administradorRepository.findAll(Sort.by("idAdmin"));

        return administradores.stream().map(admin -> {
            Usuario usuario = usuarioRepository.findFirstByAdmin(admin);

            AdministradorConCorreoDTO dto = new AdministradorConCorreoDTO();
            dto.setIdAdmin(admin.getIdAdmin());
            dto.setNombreAdmin(admin.getNombreAdmin());
            dto.setApellidoAdmin(admin.getApellidoAdmin());
            dto.setFechaNacimientoAdmin(admin.getFechaNacimientoAdmin());
            dto.setCorreo(usuario != null ? usuario.getCorreoUsuario() : null);
            dto.setEstado(usuario != null ? usuario.getEstado() : null);

            return dto;
        }).toList();
    }

    public void delete(final Integer idAdmin) {
        administradorRepository.deleteById(idAdmin);
    }

    private AdministradorDTO mapToDTO(final Administrador administrador,
            final AdministradorDTO administradorDTO) {
        administradorDTO.setIdAdmin(administrador.getIdAdmin());
        administradorDTO.setNombreAdmin(administrador.getNombreAdmin());
        administradorDTO.setApellidoAdmin(administrador.getApellidoAdmin());
        administradorDTO.setFechaNacimientoAdmin(administrador.getFechaNacimientoAdmin());
        return administradorDTO;
    }

    private Administrador mapToEntity(final AdministradorDTO administradorDTO,
            final Administrador administrador) {
        administrador.setNombreAdmin(administradorDTO.getNombreAdmin());
        administrador.setApellidoAdmin(administradorDTO.getApellidoAdmin());
        administrador.setFechaNacimientoAdmin(administradorDTO.getFechaNacimientoAdmin());
        return administrador;
    }

    public ReferencedWarning getReferencedWarning(final Integer idAdmin) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Administrador administrador = administradorRepository.findById(idAdmin)
                .orElseThrow(NotFoundException::new);
        final Usuario adminUsuario = usuarioRepository.findFirstByAdmin(administrador);
        if (adminUsuario != null) {
            referencedWarning.setKey("administrador.usuario.admin.referenced");
            referencedWarning.addParam(adminUsuario.getIdUsuario());
            return referencedWarning;
        }
        final SolicitudDominio adminSolicitudDominio = solicitudDominioRepository.findFirstByAdmin(administrador);
        if (adminSolicitudDominio != null) {
            referencedWarning.setKey("administrador.solicitudDominio.admin.referenced");
            referencedWarning.addParam(adminSolicitudDominio.getIdSolicitud());
            return referencedWarning;
        }
        return null;
    }
}
