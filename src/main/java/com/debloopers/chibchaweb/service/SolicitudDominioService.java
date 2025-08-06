package com.debloopers.chibchaweb.service;

import java.util.List;

import com.debloopers.chibchaweb.dto.ResponseDTO;
import com.debloopers.chibchaweb.entity.*;
import com.debloopers.chibchaweb.dto.SolicitudDominioDTO;
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

    @Transactional
    public List<SolicitudDominioDTO> obtenerSolicitudesPorCliente(Integer idCliente) {
        List<SolicitudDominio> solicitudes = solicitudDominioRepository.findByCliente_IdCliente(idCliente);
        return solicitudes.stream()
                .map(s -> mapToDTO(s, new SolicitudDominioDTO()))
                .toList();
    }

    @Transactional
    public List<SolicitudDominioDTO> obtenerSolicitudesPorDistribuidor(Integer idDistribuidor) {
        List<SolicitudDominio> solicitudes = solicitudDominioRepository.findByDistribuidor_IdDistribuidor(idDistribuidor);
        return solicitudes.stream()
                .map(s -> mapToDTO(s, new SolicitudDominioDTO()))
                .toList();
    }

    @Transactional
    public ResponseDTO create(final SolicitudDominioDTO solicitudDominioDTO) {

        final Dominio dominio = dominioRepository.findById(solicitudDominioDTO.getDominio())
                .orElse(null);

        if (dominio == null) {
            return new ResponseDTO(false, "The specified domain does not exist.");
        }

        String estadoDominio = dominio.getEstado();
        if ("En uso".equalsIgnoreCase(estadoDominio) || "Reservado".equalsIgnoreCase(estadoDominio)) {
            return new ResponseDTO(false, "The domain is already in use or is reserved.");
        }

        if (solicitudDominioDTO.getCliente() != null && solicitudDominioDTO.getDistribuidor() != null) {
            return new ResponseDTO(false, "Only specify customer or distributor, not both.");
        }

        if (solicitudDominioDTO.getCliente() == null && solicitudDominioDTO.getDistribuidor() == null) {
            return new ResponseDTO(false, "You must specify a customer or distributor.");
        }


        boolean solicitudExiste = false;

        if (solicitudDominioDTO.getCliente() != null) {
            solicitudExiste = solicitudDominioRepository.existsByCliente_IdClienteAndDominio_IdDominio(
                    solicitudDominioDTO.getCliente(), solicitudDominioDTO.getDominio());
        } else if (solicitudDominioDTO.getDistribuidor() != null) {
            solicitudExiste = solicitudDominioRepository.existsByDistribuidor_IdDistribuidorAndDominio_IdDominio(
                    solicitudDominioDTO.getDistribuidor(), solicitudDominioDTO.getDominio());
        }

        if (solicitudExiste) {
            return new ResponseDTO(false, "There is already a request for this domain by this customer or distributor.");
        }

        final SolicitudDominio solicitudDominio = new SolicitudDominio();
        mapToEntity(solicitudDominioDTO, solicitudDominio);
        solicitudDominioRepository.save(solicitudDominio);

        return new ResponseDTO(true, "Request successfully created.");
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