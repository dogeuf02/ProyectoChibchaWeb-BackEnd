package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.domain.PlanCliente;
import com.debloopers.chibchaweb.model.PlanClienteDTO;
import com.debloopers.chibchaweb.repos.PlanClienteRepository;
import com.debloopers.chibchaweb.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PlanClienteService {

    private final PlanClienteRepository planClienteRepository;

    public PlanClienteService(final PlanClienteRepository planClienteRepository) {
        this.planClienteRepository = planClienteRepository;
    }

    public List<PlanClienteDTO> findAll() {
        final List<PlanCliente> planClientes = planClienteRepository.findAll(Sort.by("idPc"));
        return planClientes.stream()
                .map(planCliente -> mapToDTO(planCliente, new PlanClienteDTO()))
                .toList();
    }

    public PlanClienteDTO get(final String idPc) {
        return planClienteRepository.findById(idPc)
                .map(planCliente -> mapToDTO(planCliente, new PlanClienteDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final PlanClienteDTO planClienteDTO) {
        final PlanCliente planCliente = new PlanCliente();
        mapToEntity(planClienteDTO, planCliente);
        planCliente.setIdPc(planClienteDTO.getIdPc());
        return planClienteRepository.save(planCliente).getIdPc();
    }

    public void update(final String idPc, final PlanClienteDTO planClienteDTO) {
        final PlanCliente planCliente = planClienteRepository.findById(idPc)
                .orElseThrow(NotFoundException::new);
        mapToEntity(planClienteDTO, planCliente);
        planClienteRepository.save(planCliente);
    }

    public void delete(final String idPc) {
        planClienteRepository.deleteById(idPc);
    }

    private PlanClienteDTO mapToDTO(final PlanCliente planCliente,
            final PlanClienteDTO planClienteDTO) {
        planClienteDTO.setIdPc(planCliente.getIdPc());
        planClienteDTO.setNombrePlan(planCliente.getNombrePlan());
        return planClienteDTO;
    }

    private PlanCliente mapToEntity(final PlanClienteDTO planClienteDTO,
            final PlanCliente planCliente) {
        planCliente.setNombrePlan(planClienteDTO.getNombrePlan());
        return planCliente;
    }

    public boolean idPcExists(final String idPc) {
        return planClienteRepository.existsByIdPcIgnoreCase(idPc);
    }

}
