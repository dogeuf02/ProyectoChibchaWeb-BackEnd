package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.dto.PrecioPlanDTO;
import com.debloopers.chibchaweb.entity.PlanCliente;
import com.debloopers.chibchaweb.entity.PlanPago;
import com.debloopers.chibchaweb.entity.PrecioPlan;
import com.debloopers.chibchaweb.repository.PlanClienteRepository;
import com.debloopers.chibchaweb.repository.PlanPagoRepository;
import com.debloopers.chibchaweb.repository.PrecioPlanRepository;
import com.debloopers.chibchaweb.util.NotFoundException;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PrecioPlanService {

    private final PrecioPlanRepository precioPlanRepository;
    private final PlanClienteRepository planClienteRepository;
    private final PlanPagoRepository planPagoRepository;

    public PrecioPlanService(final PrecioPlanRepository precioPlanRepository,
            final PlanClienteRepository planClienteRepository,
            final PlanPagoRepository planPagoRepository) {
        this.precioPlanRepository = precioPlanRepository;
        this.planClienteRepository = planClienteRepository;
        this.planPagoRepository = planPagoRepository;
    }

    public List<PrecioPlanDTO> findAll() {
        final List<PrecioPlan> precioPlans = precioPlanRepository.findAll(Sort.by("id"));
        return precioPlans.stream()
                .map(precioPlan -> mapToDTO(precioPlan, new PrecioPlanDTO()))
                .toList();
    }

    public PrecioPlanDTO get(final Long id) {
        return precioPlanRepository.findById(id)
                .map(precioPlan -> mapToDTO(precioPlan, new PrecioPlanDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PrecioPlanDTO precioPlanDTO) {
        final PrecioPlan precioPlan = new PrecioPlan();
        mapToEntity(precioPlanDTO, precioPlan);
        return precioPlanRepository.save(precioPlan).getId();
    }

    public void update(final Long id, final PrecioPlanDTO precioPlanDTO) {
        final PrecioPlan precioPlan = precioPlanRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(precioPlanDTO, precioPlan);
        precioPlanRepository.save(precioPlan);
    }

    public void delete(final Long id) {
        precioPlanRepository.deleteById(id);
    }

    private PrecioPlanDTO mapToDTO(final PrecioPlan precioPlan, final PrecioPlanDTO precioPlanDTO) {
        precioPlanDTO.setId(precioPlan.getId());
        precioPlanDTO.setPrecio(precioPlan.getPrecio());
        precioPlanDTO.setPlanCliente(precioPlan.getPlanCliente() == null ? null : precioPlan.getPlanCliente().getIdPlanCliente());
        precioPlanDTO.setPlanPago(precioPlan.getPlanPago() == null ? null : precioPlan.getPlanPago().getIdPlanPago());
        return precioPlanDTO;
    }

    private PrecioPlan mapToEntity(final PrecioPlanDTO precioPlanDTO, final PrecioPlan precioPlan) {
        precioPlan.setPrecio(precioPlanDTO.getPrecio());
        final PlanCliente planCliente = precioPlanDTO.getPlanCliente() == null ? null : planClienteRepository.findById(precioPlanDTO.getPlanCliente())
                .orElseThrow(() -> new NotFoundException("planCliente not found"));
        precioPlan.setPlanCliente(planCliente);
        final PlanPago planPago = precioPlanDTO.getPlanPago() == null ? null : planPagoRepository.findById(precioPlanDTO.getPlanPago())
                .orElseThrow(() -> new NotFoundException("planPago not found"));
        precioPlan.setPlanPago(planPago);
        return precioPlan;
    }
}