package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.domain.Administrador;
import com.debloopers.chibchaweb.domain.Distribuidor;
import com.debloopers.chibchaweb.domain.Dominio;
import com.debloopers.chibchaweb.domain.Registrador;
import com.debloopers.chibchaweb.domain.SolicitudDomDistribuidor;
import com.debloopers.chibchaweb.model.SolicitudDomDistribuidorDTO;
import com.debloopers.chibchaweb.repos.AdministradorRepository;
import com.debloopers.chibchaweb.repos.DistribuidorRepository;
import com.debloopers.chibchaweb.repos.DominioRepository;
import com.debloopers.chibchaweb.repos.RegistradorRepository;
import com.debloopers.chibchaweb.repos.SolicitudDomDistribuidorRepository;
import com.debloopers.chibchaweb.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class SolicitudDomDistribuidorService {

    private final SolicitudDomDistribuidorRepository solicitudDomDistribuidorRepository;
    private final DistribuidorRepository distribuidorRepository;
    private final DominioRepository dominioRepository;
    private final AdministradorRepository administradorRepository;
    private final RegistradorRepository registradorRepository;

    public SolicitudDomDistribuidorService(
            final SolicitudDomDistribuidorRepository solicitudDomDistribuidorRepository,
            final DistribuidorRepository distribuidorRepository,
            final DominioRepository dominioRepository,
            final AdministradorRepository administradorRepository,
            final RegistradorRepository registradorRepository) {
        this.solicitudDomDistribuidorRepository = solicitudDomDistribuidorRepository;
        this.distribuidorRepository = distribuidorRepository;
        this.dominioRepository = dominioRepository;
        this.administradorRepository = administradorRepository;
        this.registradorRepository = registradorRepository;
    }

    public List<SolicitudDomDistribuidorDTO> findAll() {
        final List<SolicitudDomDistribuidor> solicitudDomDistribuidors = solicitudDomDistribuidorRepository.findAll(Sort.by("tld"));
        return solicitudDomDistribuidors.stream()
                .map(solicitudDomDistribuidor -> mapToDTO(solicitudDomDistribuidor, new SolicitudDomDistribuidorDTO()))
                .toList();
    }

    public SolicitudDomDistribuidorDTO get(final String tld) {
        return solicitudDomDistribuidorRepository.findById(tld)
                .map(solicitudDomDistribuidor -> mapToDTO(solicitudDomDistribuidor, new SolicitudDomDistribuidorDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final SolicitudDomDistribuidorDTO solicitudDomDistribuidorDTO) {
        final SolicitudDomDistribuidor solicitudDomDistribuidor = new SolicitudDomDistribuidor();
        mapToEntity(solicitudDomDistribuidorDTO, solicitudDomDistribuidor);
        solicitudDomDistribuidor.setTld(solicitudDomDistribuidorDTO.getTld());
        return solicitudDomDistribuidorRepository.save(solicitudDomDistribuidor).getTld();
    }

    public void update(final String tld,
            final SolicitudDomDistribuidorDTO solicitudDomDistribuidorDTO) {
        final SolicitudDomDistribuidor solicitudDomDistribuidor = solicitudDomDistribuidorRepository.findById(tld)
                .orElseThrow(NotFoundException::new);
        mapToEntity(solicitudDomDistribuidorDTO, solicitudDomDistribuidor);
        solicitudDomDistribuidorRepository.save(solicitudDomDistribuidor);
    }

    public void delete(final String tld) {
        solicitudDomDistribuidorRepository.deleteById(tld);
    }

    private SolicitudDomDistribuidorDTO mapToDTO(
            final SolicitudDomDistribuidor solicitudDomDistribuidor,
            final SolicitudDomDistribuidorDTO solicitudDomDistribuidorDTO) {
        solicitudDomDistribuidorDTO.setTld(solicitudDomDistribuidor.getTld());
        solicitudDomDistribuidorDTO.setFechaSolicitud(solicitudDomDistribuidor.getFechaSolicitud());
        solicitudDomDistribuidorDTO.setEstadoSolicitud(solicitudDomDistribuidor.getEstadoSolicitud());
        solicitudDomDistribuidorDTO.setFechaRevision(solicitudDomDistribuidor.getFechaRevision());
        solicitudDomDistribuidorDTO.setFechaEnvio(solicitudDomDistribuidor.getFechaEnvio());
        solicitudDomDistribuidorDTO.setDistribuidor(solicitudDomDistribuidor.getDistribuidor() == null ? null : solicitudDomDistribuidor.getDistribuidor().getIdDistribuidor());
        solicitudDomDistribuidorDTO.setNombreDominio(solicitudDomDistribuidor.getNombreDominio() == null ? null : solicitudDomDistribuidor.getNombreDominio().getNombreDominio());
        solicitudDomDistribuidorDTO.setAdmin(solicitudDomDistribuidor.getAdmin() == null ? null : solicitudDomDistribuidor.getAdmin().getIdAdmin());
        solicitudDomDistribuidorDTO.setRegistrador(solicitudDomDistribuidor.getRegistrador() == null ? null : solicitudDomDistribuidor.getRegistrador().getIdRegistrador());
        return solicitudDomDistribuidorDTO;
    }

    private SolicitudDomDistribuidor mapToEntity(
            final SolicitudDomDistribuidorDTO solicitudDomDistribuidorDTO,
            final SolicitudDomDistribuidor solicitudDomDistribuidor) {
        solicitudDomDistribuidor.setFechaSolicitud(solicitudDomDistribuidorDTO.getFechaSolicitud());
        solicitudDomDistribuidor.setEstadoSolicitud(solicitudDomDistribuidorDTO.getEstadoSolicitud());
        solicitudDomDistribuidor.setFechaRevision(solicitudDomDistribuidorDTO.getFechaRevision());
        solicitudDomDistribuidor.setFechaEnvio(solicitudDomDistribuidorDTO.getFechaEnvio());
        final Distribuidor distribuidor = solicitudDomDistribuidorDTO.getDistribuidor() == null ? null : distribuidorRepository.findById(solicitudDomDistribuidorDTO.getDistribuidor())
                .orElseThrow(() -> new NotFoundException("distribuidor not found"));
        solicitudDomDistribuidor.setDistribuidor(distribuidor);
        final Dominio nombreDominio = solicitudDomDistribuidorDTO.getNombreDominio() == null ? null : dominioRepository.findById(solicitudDomDistribuidorDTO.getNombreDominio())
                .orElseThrow(() -> new NotFoundException("nombreDominio not found"));
        solicitudDomDistribuidor.setNombreDominio(nombreDominio);
        final Administrador admin = solicitudDomDistribuidorDTO.getAdmin() == null ? null : administradorRepository.findById(solicitudDomDistribuidorDTO.getAdmin())
                .orElseThrow(() -> new NotFoundException("admin not found"));
        solicitudDomDistribuidor.setAdmin(admin);
        final Registrador registrador = solicitudDomDistribuidorDTO.getRegistrador() == null ? null : registradorRepository.findById(solicitudDomDistribuidorDTO.getRegistrador())
                .orElseThrow(() -> new NotFoundException("registrador not found"));
        solicitudDomDistribuidor.setRegistrador(registrador);
        return solicitudDomDistribuidor;
    }

    public boolean tldExists(final String tld) {
        return solicitudDomDistribuidorRepository.existsByTldIgnoreCase(tld);
    }

}
