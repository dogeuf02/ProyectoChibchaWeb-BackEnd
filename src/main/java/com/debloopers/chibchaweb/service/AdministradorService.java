package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.domain.Administrador;
import com.debloopers.chibchaweb.domain.SolicitudDomCliente;
import com.debloopers.chibchaweb.domain.SolicitudDomDistribuidor;
import com.debloopers.chibchaweb.domain.Usuario;
import com.debloopers.chibchaweb.model.AdministradorActualizarDTO;
import com.debloopers.chibchaweb.model.AdministradorDTO;
import com.debloopers.chibchaweb.model.AdministradorRegistroRequestDTO;
import com.debloopers.chibchaweb.model.AdministradorRegistroResponseDTO;
import com.debloopers.chibchaweb.repos.AdministradorRepository;
import com.debloopers.chibchaweb.repos.SolicitudDomClienteRepository;
import com.debloopers.chibchaweb.repos.SolicitudDomDistribuidorRepository;
import com.debloopers.chibchaweb.repos.UsuarioRepository;
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
    private final SolicitudDomClienteRepository solicitudDomClienteRepository;
    private final SolicitudDomDistribuidorRepository solicitudDomDistribuidorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AdministradorService(final AdministradorRepository administradorRepository,
            final UsuarioRepository usuarioRepository,
            final SolicitudDomClienteRepository solicitudDomClienteRepository,
            final SolicitudDomDistribuidorRepository solicitudDomDistribuidorRepository) {
        this.administradorRepository = administradorRepository;
        this.usuarioRepository = usuarioRepository;
        this.solicitudDomClienteRepository = solicitudDomClienteRepository;
        this.solicitudDomDistribuidorRepository = solicitudDomDistribuidorRepository;
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
    public AdministradorRegistroResponseDTO create(AdministradorRegistroRequestDTO dto) {
        try {
            if (usuarioRepository.findByCorreoUsuario(dto.getCorreoAdmin()) != null) {
                return new AdministradorRegistroResponseDTO(false, "El correo ya está registrado.");
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

            return new AdministradorRegistroResponseDTO(true, "Administrador creado exitosamente.");
        } catch (Exception e) {
            return new AdministradorRegistroResponseDTO(false, "Error interno al crear el administrador.");
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
        final SolicitudDomCliente adminSolicitudDomCliente = solicitudDomClienteRepository.findFirstByAdmin(administrador);
        if (adminSolicitudDomCliente != null) {
            referencedWarning.setKey("administrador.solicitudDomCliente.admin.referenced");
            referencedWarning.addParam(adminSolicitudDomCliente.getTld());
            return referencedWarning;
        }
        final SolicitudDomDistribuidor adminSolicitudDomDistribuidor = solicitudDomDistribuidorRepository.findFirstByAdmin(administrador);
        if (adminSolicitudDomDistribuidor != null) {
            referencedWarning.setKey("administrador.solicitudDomDistribuidor.admin.referenced");
            referencedWarning.addParam(adminSolicitudDomDistribuidor.getTld());
            return referencedWarning;
        }
        return null;
    }

}
