package com.debloopers.chibchaweb.service;

import java.util.List;

import com.debloopers.chibchaweb.entity.*;
import com.debloopers.chibchaweb.dto.SolicitudDominioDTO;
import com.debloopers.chibchaweb.repository.*;
import com.debloopers.chibchaweb.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class SolicitudDominioService {

    private final SolicitudDominioRepository solicitudDominioRepository;
    private final ClienteDirectoRepository clienteDirectoRepository;
    private final DistribuidorRepository distribuidorRepository;
    private final DominioRepository dominioRepository;
    private final AdministradorRepository administradorRepository;

    public SolicitudDominioService(final SolicitudDominioRepository solicitudDominioRepository,
                                   final ClienteDirectoRepository clienteDirectoRepository,
                                   final DistribuidorRepository distribuidorRepository,
                                   final DominioRepository dominioRepository,
                                   final AdministradorRepository administradorRepository) {
        this.solicitudDominioRepository = solicitudDominioRepository;
        this.clienteDirectoRepository = clienteDirectoRepository;
        this.distribuidorRepository = distribuidorRepository;
        this.dominioRepository = dominioRepository;
        this.administradorRepository = administradorRepository;
    }

    public List<SolicitudDominioDTO> findAll() {
        final List<SolicitudDominio> solicitudDominios = solicitudDominioRepository.findAll(Sort.by("idSolicitud"));
        return solicitudDominios.stream()
                .map(solicitudDominio -> mapToDTO(solicitudDominio, new SolicitudDominioDTO()))
                .toList();
    }

    public SolicitudDominioDTO get(final Integer idSolicitud) {
        return solicitudDominioRepository.findById(idSolicitud)
                .map(solicitudDominio -> mapToDTO(solicitudDominio, new SolicitudDominioDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final SolicitudDominioDTO solicitudDominioDTO) {
        final SolicitudDominio solicitudDominio = new SolicitudDominio();
        mapToEntity(solicitudDominioDTO, solicitudDominio);
        return solicitudDominioRepository.save(solicitudDominio).getIdSolicitud();
    }

    public void update(final Integer idSolicitud, final SolicitudDominioDTO solicitudDominioDTO) {
        final SolicitudDominio solicitudDominio = solicitudDominioRepository.findById(idSolicitud)
                .orElseThrow(NotFoundException::new);
        mapToEntity(solicitudDominioDTO, solicitudDominio);
        solicitudDominioRepository.save(solicitudDominio);
    }

    public void delete(final Integer idSolicitud) {
        solicitudDominioRepository.deleteById(idSolicitud);
    }

    private SolicitudDominioDTO mapToDTO(final SolicitudDominio solicitudDominio,
                                         final SolicitudDominioDTO solicitudDominioDTO) {
        solicitudDominioDTO.setIdSolicitud(solicitudDominio.getIdSolicitud());
        solicitudDominioDTO.setEstadoSolicitud(solicitudDominio.getEstadoSolicitud());
        solicitudDominioDTO.setFechaSolicitud(solicitudDominio.getFechaSolicitud());
        solicitudDominioDTO.setFechaAprobacion(solicitudDominio.getFechaAprobacion());
        solicitudDominioDTO.setCliente(solicitudDominio.getCliente() == null ? null : solicitudDominio.getCliente().getIdCliente());
        solicitudDominioDTO.setDistribuidor(solicitudDominio.getDistribuidor() == null ? null : solicitudDominio.getDistribuidor().getIdDistribuidor());
        solicitudDominioDTO.setDominio(solicitudDominio.getDominio() == null ? null : solicitudDominio.getDominio().getIdDominio());
        solicitudDominioDTO.setAdmin(solicitudDominio.getAdmin() == null ? null : solicitudDominio.getAdmin().getIdAdmin());
        return solicitudDominioDTO;
    }

    private SolicitudDominio mapToEntity(final SolicitudDominioDTO solicitudDominioDTO,
                                         final SolicitudDominio solicitudDominio) {
        solicitudDominio.setEstadoSolicitud(solicitudDominioDTO.getEstadoSolicitud());
        solicitudDominio.setFechaSolicitud(solicitudDominioDTO.getFechaSolicitud());
        solicitudDominio.setFechaAprobacion(solicitudDominioDTO.getFechaAprobacion());
        final ClienteDirecto cliente = solicitudDominioDTO.getCliente() == null ? null : clienteDirectoRepository.findById(solicitudDominioDTO.getCliente())
                .orElseThrow(() -> new NotFoundException("cliente not found"));
        solicitudDominio.setCliente(cliente);
        final Distribuidor distribuidor = solicitudDominioDTO.getDistribuidor() == null ? null : distribuidorRepository.findById(solicitudDominioDTO.getDistribuidor())
                .orElseThrow(() -> new NotFoundException("distribuidor not found"));
        solicitudDominio.setDistribuidor(distribuidor);
        final Dominio dominio = solicitudDominioDTO.getDominio() == null ? null : dominioRepository.findById(solicitudDominioDTO.getDominio())
                .orElseThrow(() -> new NotFoundException("dominio not found"));
        solicitudDominio.setDominio(dominio);
        final Administrador admin = solicitudDominioDTO.getAdmin() == null ? null : administradorRepository.findById(solicitudDominioDTO.getAdmin())
                .orElseThrow(() -> new NotFoundException("admin not found"));
        solicitudDominio.setAdmin(admin);
        return solicitudDominio;
    }
}