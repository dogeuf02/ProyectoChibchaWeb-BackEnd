package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.entity.ClienteDirecto;
import com.debloopers.chibchaweb.entity.Plan;
import com.debloopers.chibchaweb.dto.PlanDTO;
import com.debloopers.chibchaweb.repository.ClienteDirectoRepository;
import com.debloopers.chibchaweb.repository.PlanRepository;
import com.debloopers.chibchaweb.util.NotFoundException;
import com.debloopers.chibchaweb.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PlanService {

    private final PlanRepository planRepository;
    private final ClienteDirectoRepository clienteDirectoRepository;

    public PlanService(final PlanRepository planRepository,
            final ClienteDirectoRepository clienteDirectoRepository) {
        this.planRepository = planRepository;
        this.clienteDirectoRepository = clienteDirectoRepository;
    }

    public List<PlanDTO> findAll() {
        final List<Plan> plans = planRepository.findAll(Sort.by("idPlan"));
        return plans.stream()
                .map(plan -> mapToDTO(plan, new PlanDTO()))
                .toList();
    }

    public PlanDTO get(final Integer idPlan) {
        return planRepository.findById(idPlan)
                .map(plan -> mapToDTO(plan, new PlanDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final PlanDTO planDTO) {
        final Plan plan = new Plan();
        mapToEntity(planDTO, plan);
        return planRepository.save(plan).getIdPlan();
    }

    public void update(final Integer idPlan, final PlanDTO planDTO) {
        final Plan plan = planRepository.findById(idPlan)
                .orElseThrow(NotFoundException::new);
        mapToEntity(planDTO, plan);
        planRepository.save(plan);
    }

    public void delete(final Integer idPlan) {
        planRepository.deleteById(idPlan);
    }

    private PlanDTO mapToDTO(final Plan plan, final PlanDTO planDTO) {
        planDTO.setIdPlan(plan.getIdPlan());
        planDTO.setNombrePlan(plan.getNombrePlan());
        planDTO.setPrecio(plan.getPrecio());
        return planDTO;
    }

    private Plan mapToEntity(final PlanDTO planDTO, final Plan plan) {
        plan.setNombrePlan(planDTO.getNombrePlan());
        plan.setPrecio(planDTO.getPrecio());
        return plan;
    }

    public ReferencedWarning getReferencedWarning(final Integer idPlan) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Plan plan = planRepository.findById(idPlan)
                .orElseThrow(NotFoundException::new);
        final ClienteDirecto planClienteDirecto = clienteDirectoRepository.findFirstByPlan(plan);
        if (planClienteDirecto != null) {
            referencedWarning.setKey("plan.clienteDirecto.plan.referenced");
            referencedWarning.addParam(planClienteDirecto.getIdCliente());
            return referencedWarning;
        }
        return null;
    }

}
