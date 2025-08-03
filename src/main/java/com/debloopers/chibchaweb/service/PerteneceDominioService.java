package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.dto.PerteneceDominioDTO;
import com.debloopers.chibchaweb.entity.*;
import com.debloopers.chibchaweb.repository.*;
import com.debloopers.chibchaweb.util.NotFoundException;
import com.debloopers.chibchaweb.util.ReferencedWarning;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PerteneceDominioService {

    private final PerteneceDominioRepository perteneceDominioRepository;
    private final ClienteDirectoRepository clienteDirectoRepository;
    private final DistribuidorRepository distribuidorRepository;
    private final DominioRepository dominioRepository;
    private final SolicitudTrasladoRepository solicitudTrasladoRepository;

    public PerteneceDominioService(final PerteneceDominioRepository perteneceDominioRepository,
            final ClienteDirectoRepository clienteDirectoRepository,
            final DistribuidorRepository distribuidorRepository,
            final DominioRepository dominioRepository,
            final SolicitudTrasladoRepository solicitudTrasladoRepository) {
        this.perteneceDominioRepository = perteneceDominioRepository;
        this.clienteDirectoRepository = clienteDirectoRepository;
        this.distribuidorRepository = distribuidorRepository;
        this.dominioRepository = dominioRepository;
        this.solicitudTrasladoRepository = solicitudTrasladoRepository;
    }

    public List<PerteneceDominioDTO> findAll() {
        final List<PerteneceDominio> perteneceDominios = perteneceDominioRepository.findAll(Sort.by("idPertenece"));
        return perteneceDominios.stream()
                .map(perteneceDominio -> mapToDTO(perteneceDominio, new PerteneceDominioDTO()))
                .toList();
    }

    public PerteneceDominioDTO get(final Integer idPertenece) {
        return perteneceDominioRepository.findById(idPertenece)
                .map(perteneceDominio -> mapToDTO(perteneceDominio, new PerteneceDominioDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final PerteneceDominioDTO perteneceDominioDTO) {
        final PerteneceDominio perteneceDominio = new PerteneceDominio();
        mapToEntity(perteneceDominioDTO, perteneceDominio);
        return perteneceDominioRepository.save(perteneceDominio).getIdPertenece();
    }

    public void update(final Integer idPertenece, final PerteneceDominioDTO perteneceDominioDTO) {
        final PerteneceDominio perteneceDominio = perteneceDominioRepository.findById(idPertenece)
                .orElseThrow(NotFoundException::new);
        mapToEntity(perteneceDominioDTO, perteneceDominio);
        perteneceDominioRepository.save(perteneceDominio);
    }

    public void delete(final Integer idPertenece) {
        perteneceDominioRepository.deleteById(idPertenece);
    }

    private PerteneceDominioDTO mapToDTO(final PerteneceDominio perteneceDominio,
            final PerteneceDominioDTO perteneceDominioDTO) {
        perteneceDominioDTO.setIdPertenece(perteneceDominio.getIdPertenece());
        perteneceDominioDTO.setCliente(perteneceDominio.getCliente() == null ? null : perteneceDominio.getCliente().getIdCliente());
        perteneceDominioDTO.setDistribuidor(perteneceDominio.getDistribuidor() == null ? null : perteneceDominio.getDistribuidor().getIdDistribuidor());
        perteneceDominioDTO.setDominio(perteneceDominio.getDominio() == null ? null : perteneceDominio.getDominio().getIdDominio());
        return perteneceDominioDTO;
    }

    private PerteneceDominio mapToEntity(final PerteneceDominioDTO perteneceDominioDTO,
            final PerteneceDominio perteneceDominio) {
        final ClienteDirecto cliente = perteneceDominioDTO.getCliente() == null ? null : clienteDirectoRepository.findById(perteneceDominioDTO.getCliente())
                .orElseThrow(() -> new NotFoundException("cliente not found"));
        perteneceDominio.setCliente(cliente);
        final Distribuidor distribuidor = perteneceDominioDTO.getDistribuidor() == null ? null : distribuidorRepository.findById(perteneceDominioDTO.getDistribuidor())
                .orElseThrow(() -> new NotFoundException("distribuidor not found"));
        perteneceDominio.setDistribuidor(distribuidor);
        final Dominio dominio = perteneceDominioDTO.getDominio() == null ? null : dominioRepository.findById(perteneceDominioDTO.getDominio())
                .orElseThrow(() -> new NotFoundException("dominio not found"));
        perteneceDominio.setDominio(dominio);
        return perteneceDominio;
    }

    public ReferencedWarning getReferencedWarning(final Integer idPertenece) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final PerteneceDominio perteneceDominio = perteneceDominioRepository.findById(idPertenece)
                .orElseThrow(NotFoundException::new);
        final SolicitudTraslado perteneceSolicitudTraslado = solicitudTrasladoRepository.findFirstByPertenece(perteneceDominio);
        if (perteneceSolicitudTraslado != null) {
            referencedWarning.setKey("perteneceDominio.solicitudTraslado.pertenece.referenced");
            referencedWarning.addParam(perteneceSolicitudTraslado.getIdSolicitudTraslado());
            return referencedWarning;
        }
        return null;
    }
}