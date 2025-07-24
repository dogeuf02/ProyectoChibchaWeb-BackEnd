package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.domain.Registrador;
import com.debloopers.chibchaweb.domain.SolicitudDomCd;
import com.debloopers.chibchaweb.domain.SolicitudDomDistribuidor;
import com.debloopers.chibchaweb.model.RegistradorDTO;
import com.debloopers.chibchaweb.repos.RegistradorRepository;
import com.debloopers.chibchaweb.repos.SolicitudDomCdRepository;
import com.debloopers.chibchaweb.repos.SolicitudDomDistribuidorRepository;
import com.debloopers.chibchaweb.util.NotFoundException;
import com.debloopers.chibchaweb.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class RegistradorService {

    private final RegistradorRepository registradorRepository;
    private final SolicitudDomCdRepository solicitudDomCdRepository;
    private final SolicitudDomDistribuidorRepository solicitudDomDistribuidorRepository;

    public RegistradorService(final RegistradorRepository registradorRepository,
            final SolicitudDomCdRepository solicitudDomCdRepository,
            final SolicitudDomDistribuidorRepository solicitudDomDistribuidorRepository) {
        this.registradorRepository = registradorRepository;
        this.solicitudDomCdRepository = solicitudDomCdRepository;
        this.solicitudDomDistribuidorRepository = solicitudDomDistribuidorRepository;
    }

    public List<RegistradorDTO> findAll() {
        final List<Registrador> registradors = registradorRepository.findAll(Sort.by("idRegistrador"));
        return registradors.stream()
                .map(registrador -> mapToDTO(registrador, new RegistradorDTO()))
                .toList();
    }

    public RegistradorDTO get(final String idRegistrador) {
        return registradorRepository.findById(idRegistrador)
                .map(registrador -> mapToDTO(registrador, new RegistradorDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final RegistradorDTO registradorDTO) {
        final Registrador registrador = new Registrador();
        mapToEntity(registradorDTO, registrador);
        registrador.setIdRegistrador(registradorDTO.getIdRegistrador());
        return registradorRepository.save(registrador).getIdRegistrador();
    }

    public void update(final String idRegistrador, final RegistradorDTO registradorDTO) {
        final Registrador registrador = registradorRepository.findById(idRegistrador)
                .orElseThrow(NotFoundException::new);
        mapToEntity(registradorDTO, registrador);
        registradorRepository.save(registrador);
    }

    public void delete(final String idRegistrador) {
        registradorRepository.deleteById(idRegistrador);
    }

    private RegistradorDTO mapToDTO(final Registrador registrador,
            final RegistradorDTO registradorDTO) {
        registradorDTO.setIdRegistrador(registrador.getIdRegistrador());
        registradorDTO.setNombreRegistrador(registrador.getNombreRegistrador());
        registradorDTO.setCorreoRegistrador(registrador.getCorreoRegistrador());
        return registradorDTO;
    }

    private Registrador mapToEntity(final RegistradorDTO registradorDTO,
            final Registrador registrador) {
        registrador.setNombreRegistrador(registradorDTO.getNombreRegistrador());
        registrador.setCorreoRegistrador(registradorDTO.getCorreoRegistrador());
        return registrador;
    }

    public boolean idRegistradorExists(final String idRegistrador) {
        return registradorRepository.existsByIdRegistradorIgnoreCase(idRegistrador);
    }

    public ReferencedWarning getReferencedWarning(final String idRegistrador) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Registrador registrador = registradorRepository.findById(idRegistrador)
                .orElseThrow(NotFoundException::new);
        final SolicitudDomCd registradorSolicitudDomCd = solicitudDomCdRepository.findFirstByRegistrador(registrador);
        if (registradorSolicitudDomCd != null) {
            referencedWarning.setKey("registrador.solicitudDomCd.registrador.referenced");
            referencedWarning.addParam(registradorSolicitudDomCd.getTld());
            return referencedWarning;
        }
        final SolicitudDomDistribuidor registradorSolicitudDomDistribuidor = solicitudDomDistribuidorRepository.findFirstByRegistrador(registrador);
        if (registradorSolicitudDomDistribuidor != null) {
            referencedWarning.setKey("registrador.solicitudDomDistribuidor.registrador.referenced");
            referencedWarning.addParam(registradorSolicitudDomDistribuidor.getTld());
            return referencedWarning;
        }
        return null;
    }

}
