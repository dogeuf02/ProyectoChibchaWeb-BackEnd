package com.debloopers.chibchaweb.service;

import java.util.List;

import com.debloopers.chibchaweb.entity.*;
import com.debloopers.chibchaweb.dto.SolicitudDominioActualizarDTO;
import com.debloopers.chibchaweb.dto.SolicitudDominioDTO;
import com.debloopers.chibchaweb.dto.SolicitudDominioRegistroRequestDTO;
import com.debloopers.chibchaweb.dto.SolicitudDominioRegistroResponseDTO;
import com.debloopers.chibchaweb.repository.*;
import com.debloopers.chibchaweb.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class SolicitudDominioService {

    private final SolicitudDominioRepository solicitudDominioRepository;
    private final ClienteDirectoRepository clienteDirectoRepository;
    private final DistribuidorRepository distribuidorRepository;
    private final TldRepository tldRepository;
    private final AdministradorRepository administradorRepository;

    public SolicitudDominioService(final SolicitudDominioRepository solicitudDominioRepository,
            final ClienteDirectoRepository clienteDirectoRepository,
            final DistribuidorRepository distribuidorRepository, final TldRepository tldRepository,
            final AdministradorRepository administradorRepository) {
        this.solicitudDominioRepository = solicitudDominioRepository;
        this.clienteDirectoRepository = clienteDirectoRepository;
        this.distribuidorRepository = distribuidorRepository;
        this.tldRepository = tldRepository;
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

    @Transactional
    public SolicitudDominioRegistroResponseDTO create(SolicitudDominioRegistroRequestDTO dto) {
        try {
            SolicitudDominio solicitud = new SolicitudDominio();
            solicitud.setNombreDominio(dto.getNombreDominio());
            solicitud.setEstadoDominio(dto.getEstadoDominio());
            solicitud.setEstadoSolicitud(dto.getEstadoSolicitud());
            solicitud.setFechaSolicitud(dto.getFechaSolicitud());
            solicitud.setFechaAprobacion(dto.getFechaAprobacion());

            if (dto.getCliente() != null) {
                ClienteDirecto cliente = clienteDirectoRepository.findById(dto.getCliente())
                        .orElseThrow(() -> new NotFoundException("Customer not found"));
                solicitud.setCliente(cliente);
            }

            if (dto.getDistribuidor() != null) {
                Distribuidor distribuidor = distribuidorRepository.findById(dto.getDistribuidor())
                        .orElseThrow(() -> new NotFoundException("Distributor not found"));
                solicitud.setDistribuidor(distribuidor);
            }

            if (dto.getAdmin() != null) {
                Administrador admin = administradorRepository.findById(dto.getAdmin())
                        .orElseThrow(() -> new NotFoundException("Administrator not found"));
                solicitud.setAdmin(admin);
            }

            Tld tld = tldRepository.findById(dto.getTld())
                    .orElseThrow(() -> new NotFoundException("TLD not found"));
            solicitud.setTld(tld);

            Integer id = solicitudDominioRepository.save(solicitud).getIdSolicitud();
            return new SolicitudDominioRegistroResponseDTO(true, "Application successfully created", id);

        } catch (Exception e) {
            return new SolicitudDominioRegistroResponseDTO(false, "Error creating request", null);
        }
    }

    public void update(final Integer idSolicitud, final SolicitudDominioActualizarDTO solicitudDominioDTO) {
        final SolicitudDominio solicitudDominio = solicitudDominioRepository.findById(idSolicitud)
                .orElseThrow(NotFoundException::new);

        if (solicitudDominioDTO.getEstadoDominio() != null && !solicitudDominioDTO.getEstadoDominio().isBlank()) {
            solicitudDominio.setEstadoDominio(solicitudDominioDTO.getEstadoDominio().trim());
        }

        if (solicitudDominioDTO.getEstadoSolicitud() != null && !solicitudDominioDTO.getEstadoSolicitud().isBlank()) {
            solicitudDominio.setEstadoSolicitud(solicitudDominioDTO.getEstadoSolicitud().trim());
        }

        if (solicitudDominioDTO.getFechaSolicitud() != null) {
            solicitudDominio.setFechaSolicitud(solicitudDominioDTO.getFechaSolicitud());
        }

        if (solicitudDominioDTO.getFechaAprobacion() != null) {
            solicitudDominio.setFechaAprobacion(solicitudDominioDTO.getFechaAprobacion());
        }
        // No se actualiza cliente,distribuidor,admin,nombre de dominio,Tld
        solicitudDominioRepository.save(solicitudDominio);
    }

    public void delete(final Integer idSolicitud) {
        solicitudDominioRepository.deleteById(idSolicitud);
    }

    private SolicitudDominioDTO mapToDTO(final SolicitudDominio solicitudDominio,
            final SolicitudDominioDTO solicitudDominioDTO) {
        solicitudDominioDTO.setIdSolicitud(solicitudDominio.getIdSolicitud());
        solicitudDominioDTO.setNombreDominio(solicitudDominio.getNombreDominio());
        solicitudDominioDTO.setEstadoDominio(solicitudDominio.getEstadoDominio());
        solicitudDominioDTO.setEstadoSolicitud(solicitudDominio.getEstadoSolicitud());
        solicitudDominioDTO.setFechaSolicitud(solicitudDominio.getFechaSolicitud());
        solicitudDominioDTO.setFechaAprobacion(solicitudDominio.getFechaAprobacion());
        solicitudDominioDTO.setCliente(solicitudDominio.getCliente() == null ? null : solicitudDominio.getCliente().getIdCliente());
        solicitudDominioDTO.setDistribuidor(solicitudDominio.getDistribuidor() == null ? null : solicitudDominio.getDistribuidor().getIdDistribuidor());
        solicitudDominioDTO.setTld(solicitudDominio.getTld() == null ? null : solicitudDominio.getTld().getTld());
        solicitudDominioDTO.setAdmin(solicitudDominio.getAdmin() == null ? null : solicitudDominio.getAdmin().getIdAdmin());
        return solicitudDominioDTO;
    }

    private SolicitudDominio mapToEntity(final SolicitudDominioDTO solicitudDominioDTO,
            final SolicitudDominio solicitudDominio) {
        solicitudDominio.setNombreDominio(solicitudDominioDTO.getNombreDominio());
        solicitudDominio.setEstadoDominio(solicitudDominioDTO.getEstadoDominio());
        solicitudDominio.setEstadoSolicitud(solicitudDominioDTO.getEstadoSolicitud());
        solicitudDominio.setFechaSolicitud(solicitudDominioDTO.getFechaSolicitud());
        solicitudDominio.setFechaAprobacion(solicitudDominioDTO.getFechaAprobacion());
        final ClienteDirecto cliente = solicitudDominioDTO.getCliente() == null ? null : clienteDirectoRepository.findById(solicitudDominioDTO.getCliente())
                .orElseThrow(() -> new NotFoundException("cliente not found"));
        solicitudDominio.setCliente(cliente);
        final Distribuidor distribuidor = solicitudDominioDTO.getDistribuidor() == null ? null : distribuidorRepository.findById(solicitudDominioDTO.getDistribuidor())
                .orElseThrow(() -> new NotFoundException("distribuidor not found"));
        solicitudDominio.setDistribuidor(distribuidor);
        final Tld tld = solicitudDominioDTO.getTld() == null ? null : tldRepository.findById(solicitudDominioDTO.getTld())
                .orElseThrow(() -> new NotFoundException("tld not found"));
        solicitudDominio.setTld(tld);
        final Administrador admin = solicitudDominioDTO.getAdmin() == null ? null : administradorRepository.findById(solicitudDominioDTO.getAdmin())
                .orElseThrow(() -> new NotFoundException("admin not found"));
        solicitudDominio.setAdmin(admin);
        return solicitudDominio;
    }

}
