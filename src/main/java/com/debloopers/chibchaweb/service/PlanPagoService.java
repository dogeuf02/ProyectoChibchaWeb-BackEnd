package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.dto.PlanPagoDTO;
import com.debloopers.chibchaweb.entity.PlanAdquirido;
import com.debloopers.chibchaweb.entity.PlanPago;
import com.debloopers.chibchaweb.entity.PrecioPlan;
import com.debloopers.chibchaweb.repository.PlanAdquiridoRepository;
import com.debloopers.chibchaweb.repository.PlanPagoRepository;
import com.debloopers.chibchaweb.repository.PrecioPlanRepository;
import com.debloopers.chibchaweb.util.NotFoundException;
import com.debloopers.chibchaweb.util.ReferencedWarning;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PlanPagoService {

    private final PlanPagoRepository planPagoRepository;
    private final PlanAdquiridoRepository planAdquiridoRepository;
    private final PrecioPlanRepository precioPlanRepository;

    public PlanPagoService(final PlanPagoRepository planPagoRepository,
            final PlanAdquiridoRepository planAdquiridoRepository,
            final PrecioPlanRepository precioPlanRepository) {
        this.planPagoRepository = planPagoRepository;
        this.planAdquiridoRepository = planAdquiridoRepository;
        this.precioPlanRepository = precioPlanRepository;
    }

    public List<PlanPagoDTO> findAll() {
        final List<PlanPago> planPagoes = planPagoRepository.findAll(Sort.by("idPlanPago"));
        return planPagoes.stream()
                .map(planPago -> mapToDTO(planPago, new PlanPagoDTO()))
                .toList();
    }

    public PlanPagoDTO get(final Integer idPlanPago) {
        return planPagoRepository.findById(idPlanPago)
                .map(planPago -> mapToDTO(planPago, new PlanPagoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final PlanPagoDTO planPagoDTO) {
        final PlanPago planPago = new PlanPago();
        mapToEntity(planPagoDTO, planPago);
        return planPagoRepository.save(planPago).getIdPlanPago();
    }

    public void update(final Integer idPlanPago, final PlanPagoDTO planPagoDTO) {
        final PlanPago planPago = planPagoRepository.findById(idPlanPago)
                .orElseThrow(NotFoundException::new);
        mapToEntity(planPagoDTO, planPago);
        planPagoRepository.save(planPago);
    }

    public void delete(final Integer idPlanPago) {
        planPagoRepository.deleteById(idPlanPago);
    }

    private PlanPagoDTO mapToDTO(final PlanPago planPago, final PlanPagoDTO planPagoDTO) {
        planPagoDTO.setIdPlanPago(planPago.getIdPlanPago());
        planPagoDTO.setIntervaloPago(planPago.getIntervaloPago());
        return planPagoDTO;
    }

    private PlanPago mapToEntity(final PlanPagoDTO planPagoDTO, final PlanPago planPago) {
        planPago.setIntervaloPago(planPagoDTO.getIntervaloPago());
        return planPago;
    }

    public ReferencedWarning getReferencedWarning(final Integer idPlanPago) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final PlanPago planPago = planPagoRepository.findById(idPlanPago)
                .orElseThrow(NotFoundException::new);
        final PlanAdquirido planPagoPlanAdquirido = planAdquiridoRepository.findFirstByPlanPago(planPago);
        if (planPagoPlanAdquirido != null) {
            referencedWarning.setKey("planPago.planAdquirido.planPago.referenced");
            referencedWarning.addParam(planPagoPlanAdquirido.getIdPlanAdquirido());
            return referencedWarning;
        }
        final PrecioPlan planPagoPrecioPlan = precioPlanRepository.findFirstByPlanPago(planPago);
        if (planPagoPrecioPlan != null) {
            referencedWarning.setKey("planPago.precioPlan.planPago.referenced");
            referencedWarning.addParam(planPagoPrecioPlan.getId());
            return referencedWarning;
        }
        return null;
    }
}