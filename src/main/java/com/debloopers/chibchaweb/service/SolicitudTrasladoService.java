package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.dto.SolicitudTrasladoDTO;
import com.debloopers.chibchaweb.entity.*;
import com.debloopers.chibchaweb.repository.*;
import com.debloopers.chibchaweb.util.NotFoundException;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class SolicitudTrasladoService {

    private final SolicitudTrasladoRepository solicitudTrasladoRepository;
    private final PerteneceDominioRepository perteneceDominioRepository;
    private final ClienteDirectoRepository clienteDirectoRepository;
    private final DistribuidorRepository distribuidorRepository;
    private final AdministradorRepository administradorRepository;

    public SolicitudTrasladoService(final SolicitudTrasladoRepository solicitudTrasladoRepository,
            final PerteneceDominioRepository perteneceDominioRepository,
            final ClienteDirectoRepository clienteDirectoRepository,
            final DistribuidorRepository distribuidorRepository,
            final AdministradorRepository administradorRepository) {
        this.solicitudTrasladoRepository = solicitudTrasladoRepository;
        this.perteneceDominioRepository = perteneceDominioRepository;
        this.clienteDirectoRepository = clienteDirectoRepository;
        this.distribuidorRepository = distribuidorRepository;
        this.administradorRepository = administradorRepository;
    }

    public List<SolicitudTrasladoDTO> findAll() {
        final List<SolicitudTraslado> solicitudTrasladoes = solicitudTrasladoRepository.findAll(Sort.by("idSolicitudTraslado"));
        return solicitudTrasladoes.stream()
                .map(solicitudTraslado -> mapToDTO(solicitudTraslado, new SolicitudTrasladoDTO()))
                .toList();
    }

    public SolicitudTrasladoDTO get(final Integer idSolicitudTraslado) {
        return solicitudTrasladoRepository.findById(idSolicitudTraslado)
                .map(solicitudTraslado -> mapToDTO(solicitudTraslado, new SolicitudTrasladoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public List<SolicitudTrasladoDTO> obtenerPorIdCliente(Integer idCliente) {
        List<SolicitudTraslado> solicitudes = solicitudTrasladoRepository.findByCliente_IdCliente(idCliente);
        return solicitudes.stream()
                .map(s -> mapToDTO(s, new SolicitudTrasladoDTO()))
                .toList();
    }

    public List<SolicitudTrasladoDTO> obtenerPorIdDistribuidor(Integer idDistribuidor) {
        List<SolicitudTraslado> solicitudes = solicitudTrasladoRepository.findByDistribuidor_IdDistribuidor(idDistribuidor);
        return solicitudes.stream()
                .map(s -> mapToDTO(s, new SolicitudTrasladoDTO()))
                .toList();
    }

    public Integer create(final SolicitudTrasladoDTO solicitudTrasladoDTO) {
        final SolicitudTraslado solicitudTraslado = new SolicitudTraslado();
        mapToEntity(solicitudTrasladoDTO, solicitudTraslado);
        return solicitudTrasladoRepository.save(solicitudTraslado).getIdSolicitudTraslado();
    }

    public void update(final Integer idSolicitudTraslado,
            final SolicitudTrasladoDTO solicitudTrasladoDTO) {
        final SolicitudTraslado solicitudTraslado = solicitudTrasladoRepository.findById(idSolicitudTraslado)
                .orElseThrow(NotFoundException::new);
        mapToEntity(solicitudTrasladoDTO, solicitudTraslado);
        solicitudTrasladoRepository.save(solicitudTraslado);
    }

    public void delete(final Integer idSolicitudTraslado) {
        solicitudTrasladoRepository.deleteById(idSolicitudTraslado);
    }

    private SolicitudTrasladoDTO mapToDTO(final SolicitudTraslado solicitudTraslado,
            final SolicitudTrasladoDTO solicitudTrasladoDTO) {
        solicitudTrasladoDTO.setIdSolicitudTraslado(solicitudTraslado.getIdSolicitudTraslado());
        solicitudTrasladoDTO.setFechaSolicitudTraslado(solicitudTraslado.getFechaSolicitudTraslado());
        solicitudTrasladoDTO.setFechaAprobacionTraslado(solicitudTraslado.getFechaAprobacionTraslado());
        solicitudTrasladoDTO.setEstadoTraslado(solicitudTraslado.getEstadoTraslado());
        solicitudTrasladoDTO.setPertenece(solicitudTraslado.getPertenece() == null ? null : solicitudTraslado.getPertenece().getIdPertenece());
        solicitudTrasladoDTO.setCliente(solicitudTraslado.getCliente() == null ? null : solicitudTraslado.getCliente().getIdCliente());
        solicitudTrasladoDTO.setDistribuidor(solicitudTraslado.getDistribuidor() == null ? null : solicitudTraslado.getDistribuidor().getIdDistribuidor());
        solicitudTrasladoDTO.setAdmin(solicitudTraslado.getAdmin() == null ? null : solicitudTraslado.getAdmin().getIdAdmin());
        return solicitudTrasladoDTO;
    }

    private SolicitudTraslado mapToEntity(final SolicitudTrasladoDTO solicitudTrasladoDTO,
            final SolicitudTraslado solicitudTraslado) {
        solicitudTraslado.setFechaSolicitudTraslado(solicitudTrasladoDTO.getFechaSolicitudTraslado());
        solicitudTraslado.setFechaAprobacionTraslado(solicitudTrasladoDTO.getFechaAprobacionTraslado());
        solicitudTraslado.setEstadoTraslado(solicitudTrasladoDTO.getEstadoTraslado());
        final PerteneceDominio pertenece = solicitudTrasladoDTO.getPertenece() == null ? null : perteneceDominioRepository.findById(solicitudTrasladoDTO.getPertenece())
                .orElseThrow(() -> new NotFoundException("pertenece not found"));
        solicitudTraslado.setPertenece(pertenece);
        final ClienteDirecto cliente = solicitudTrasladoDTO.getCliente() == null ? null : clienteDirectoRepository.findById(solicitudTrasladoDTO.getCliente())
                .orElseThrow(() -> new NotFoundException("cliente not found"));
        solicitudTraslado.setCliente(cliente);
        final Distribuidor distribuidor = solicitudTrasladoDTO.getDistribuidor() == null ? null : distribuidorRepository.findById(solicitudTrasladoDTO.getDistribuidor())
                .orElseThrow(() -> new NotFoundException("distribuidor not found"));
        solicitudTraslado.setDistribuidor(distribuidor);
        final Administrador admin = solicitudTrasladoDTO.getAdmin() == null ? null : administradorRepository.findById(solicitudTrasladoDTO.getAdmin())
                .orElseThrow(() -> new NotFoundException("admin not found"));
        solicitudTraslado.setAdmin(admin);
        return solicitudTraslado;
    }
}