package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.domain.Administrador;
import com.debloopers.chibchaweb.domain.SolicitudDomCd;
import com.debloopers.chibchaweb.domain.SolicitudDomDistribuidor;
import com.debloopers.chibchaweb.model.AdministradorDTO;
import com.debloopers.chibchaweb.repos.AdministradorRepository;
import com.debloopers.chibchaweb.repos.SolicitudDomCdRepository;
import com.debloopers.chibchaweb.repos.SolicitudDomDistribuidorRepository;
import com.debloopers.chibchaweb.util.NotFoundException;
import com.debloopers.chibchaweb.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class AdministradorService {

    private final AdministradorRepository administradorRepository;
    private final SolicitudDomCdRepository solicitudDomCdRepository;
    private final SolicitudDomDistribuidorRepository solicitudDomDistribuidorRepository;

    public AdministradorService(final AdministradorRepository administradorRepository,
            final SolicitudDomCdRepository solicitudDomCdRepository,
            final SolicitudDomDistribuidorRepository solicitudDomDistribuidorRepository) {
        this.administradorRepository = administradorRepository;
        this.solicitudDomCdRepository = solicitudDomCdRepository;
        this.solicitudDomDistribuidorRepository = solicitudDomDistribuidorRepository;
    }

    public List<AdministradorDTO> findAll() {
        final List<Administrador> administradors = administradorRepository.findAll(Sort.by("idAdmin"));
        return administradors.stream()
                .map(administrador -> mapToDTO(administrador, new AdministradorDTO()))
                .toList();
    }

    public AdministradorDTO get(final String idAdmin) {
        return administradorRepository.findById(idAdmin)
                .map(administrador -> mapToDTO(administrador, new AdministradorDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final AdministradorDTO administradorDTO) {
        final Administrador administrador = new Administrador();
        mapToEntity(administradorDTO, administrador);
        administrador.setIdAdmin(administradorDTO.getIdAdmin());
        return administradorRepository.save(administrador).getIdAdmin();
    }

    public void update(final String idAdmin, final AdministradorDTO administradorDTO) {
        final Administrador administrador = administradorRepository.findById(idAdmin)
                .orElseThrow(NotFoundException::new);
        mapToEntity(administradorDTO, administrador);
        administradorRepository.save(administrador);
    }

    public void delete(final String idAdmin) {
        administradorRepository.deleteById(idAdmin);
    }

    private AdministradorDTO mapToDTO(final Administrador administrador,
            final AdministradorDTO administradorDTO) {
        administradorDTO.setIdAdmin(administrador.getIdAdmin());
        administradorDTO.setNombreAdmin(administrador.getNombreAdmin());
        administradorDTO.setApellidoAdmin(administrador.getApellidoAdmin());
        administradorDTO.setCorreoAdmin(administrador.getCorreoAdmin());
        administradorDTO.setContrasenaAdmin(administrador.getContrasenaAdmin());
        administradorDTO.setFechaNacimientoAdmin(administrador.getFechaNacimientoAdmin());
        return administradorDTO;
    }

    private Administrador mapToEntity(final AdministradorDTO administradorDTO,
            final Administrador administrador) {
        administrador.setNombreAdmin(administradorDTO.getNombreAdmin());
        administrador.setApellidoAdmin(administradorDTO.getApellidoAdmin());
        administrador.setCorreoAdmin(administradorDTO.getCorreoAdmin());
        administrador.setContrasenaAdmin(administradorDTO.getContrasenaAdmin());
        administrador.setFechaNacimientoAdmin(administradorDTO.getFechaNacimientoAdmin());
        return administrador;
    }

    public boolean idAdminExists(final String idAdmin) {
        return administradorRepository.existsByIdAdminIgnoreCase(idAdmin);
    }

    public ReferencedWarning getReferencedWarning(final String idAdmin) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Administrador administrador = administradorRepository.findById(idAdmin)
                .orElseThrow(NotFoundException::new);
        final SolicitudDomCd adminSolicitudDomCd = solicitudDomCdRepository.findFirstByAdmin(administrador);
        if (adminSolicitudDomCd != null) {
            referencedWarning.setKey("administrador.solicitudDomCd.admin.referenced");
            referencedWarning.addParam(adminSolicitudDomCd.getTld());
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
