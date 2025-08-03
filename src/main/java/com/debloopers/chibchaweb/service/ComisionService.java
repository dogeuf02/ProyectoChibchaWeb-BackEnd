package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.dto.ComisionDTO;
import com.debloopers.chibchaweb.entity.Comision;
import com.debloopers.chibchaweb.entity.Distribuidor;
import com.debloopers.chibchaweb.entity.MedioPago;
import com.debloopers.chibchaweb.repository.ComisionRepository;
import com.debloopers.chibchaweb.repository.DistribuidorRepository;
import com.debloopers.chibchaweb.repository.MedioPagoRepository;
import com.debloopers.chibchaweb.util.NotFoundException;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ComisionService {

    private final ComisionRepository comisionRepository;
    private final DistribuidorRepository distribuidorRepository;
    private final MedioPagoRepository medioPagoRepository;

    public ComisionService(final ComisionRepository comisionRepository,
            final DistribuidorRepository distribuidorRepository,
            final MedioPagoRepository medioPagoRepository) {
        this.comisionRepository = comisionRepository;
        this.distribuidorRepository = distribuidorRepository;
        this.medioPagoRepository = medioPagoRepository;
    }

    public List<ComisionDTO> findAll() {
        final List<Comision> comisions = comisionRepository.findAll(Sort.by("idComision"));
        return comisions.stream()
                .map(comision -> mapToDTO(comision, new ComisionDTO()))
                .toList();
    }

    public ComisionDTO get(final Integer idComision) {
        return comisionRepository.findById(idComision)
                .map(comision -> mapToDTO(comision, new ComisionDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final ComisionDTO comisionDTO) {
        final Comision comision = new Comision();
        mapToEntity(comisionDTO, comision);
        return comisionRepository.save(comision).getIdComision();
    }

    public void update(final Integer idComision, final ComisionDTO comisionDTO) {
        final Comision comision = comisionRepository.findById(idComision)
                .orElseThrow(NotFoundException::new);
        mapToEntity(comisionDTO, comision);
        comisionRepository.save(comision);
    }

    public void delete(final Integer idComision) {
        comisionRepository.deleteById(idComision);
    }

    private ComisionDTO mapToDTO(final Comision comision, final ComisionDTO comisionDTO) {
        comisionDTO.setIdComision(comision.getIdComision());
        comisionDTO.setValorComision(comision.getValorComision());
        comisionDTO.setFechaPago(comision.getFechaPago());
        comisionDTO.setEstadoComision(comision.getEstadoComision());
        comisionDTO.setDistribuidor(comision.getDistribuidor() == null ? null : comision.getDistribuidor().getIdDistribuidor());
        comisionDTO.setMedioPago(comision.getMedioPago() == null ? null : comision.getMedioPago().getIdMedioPago());
        return comisionDTO;
    }

    private Comision mapToEntity(final ComisionDTO comisionDTO, final Comision comision) {
        comision.setValorComision(comisionDTO.getValorComision());
        comision.setFechaPago(comisionDTO.getFechaPago());
        comision.setEstadoComision(comisionDTO.getEstadoComision());
        final Distribuidor distribuidor = comisionDTO.getDistribuidor() == null ? null : distribuidorRepository.findById(comisionDTO.getDistribuidor())
                .orElseThrow(() -> new NotFoundException("distribuidor not found"));
        comision.setDistribuidor(distribuidor);
        final MedioPago medioPago = comisionDTO.getMedioPago() == null ? null : medioPagoRepository.findById(comisionDTO.getMedioPago())
                .orElseThrow(() -> new NotFoundException("medioPago not found"));
        comision.setMedioPago(medioPago);
        return comision;
    }
}