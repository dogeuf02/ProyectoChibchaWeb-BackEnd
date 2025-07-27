package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.domain.Administrador;
import com.debloopers.chibchaweb.domain.SolicitudDomCliente;
import com.debloopers.chibchaweb.domain.SolicitudDomDistribuidor;
import com.debloopers.chibchaweb.domain.Usuario;
import com.debloopers.chibchaweb.model.AdministradorDTO;
import com.debloopers.chibchaweb.repos.AdministradorRepository;
import com.debloopers.chibchaweb.repos.SolicitudDomClienteRepository;
import com.debloopers.chibchaweb.repos.SolicitudDomDistribuidorRepository;
import com.debloopers.chibchaweb.repos.UsuarioRepository;
import com.debloopers.chibchaweb.util.NotFoundException;
import com.debloopers.chibchaweb.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class AdministradorService {

    private final AdministradorRepository administradorRepository;
    private final UsuarioRepository usuarioRepository;
    private final SolicitudDomClienteRepository solicitudDomClienteRepository;
    private final SolicitudDomDistribuidorRepository solicitudDomDistribuidorRepository;

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

    public Integer create(final AdministradorDTO administradorDTO) {
        final Administrador administrador = new Administrador();
        mapToEntity(administradorDTO, administrador);
        return administradorRepository.save(administrador).getIdAdmin();
    }

    public void update(final Integer idAdmin, final AdministradorDTO administradorDTO) {
        final Administrador administrador = administradorRepository.findById(idAdmin)
                .orElseThrow(NotFoundException::new);
        mapToEntity(administradorDTO, administrador);
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
