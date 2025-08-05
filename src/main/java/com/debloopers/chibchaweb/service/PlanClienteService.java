package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.dto.PlanClienteDTO;
import com.debloopers.chibchaweb.dto.PlanPagoDTO;
import com.debloopers.chibchaweb.dto.PrecioPlanInfoDTO;
import com.debloopers.chibchaweb.entity.*;
import com.debloopers.chibchaweb.repository.ClienteDirectoRepository;
import com.debloopers.chibchaweb.repository.PlanAdquiridoRepository;
import com.debloopers.chibchaweb.repository.PlanClienteRepository;
import com.debloopers.chibchaweb.repository.PrecioPlanRepository;
import com.debloopers.chibchaweb.util.NotFoundException;
import com.debloopers.chibchaweb.util.ReferencedWarning;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class PlanClienteService {

    private final PlanClienteRepository planClienteRepository;
    private final ClienteDirectoRepository clienteDirectoRepository;
    private final PlanAdquiridoRepository planAdquiridoRepository;
    private final PrecioPlanRepository precioPlanRepository;

    public PlanClienteService(final PlanClienteRepository planClienteRepository,
            final ClienteDirectoRepository clienteDirectoRepository,
            final PlanAdquiridoRepository planAdquiridoRepository,
            final PrecioPlanRepository precioPlanRepository) {
        this.planClienteRepository = planClienteRepository;
        this.clienteDirectoRepository = clienteDirectoRepository;
        this.planAdquiridoRepository = planAdquiridoRepository;
        this.precioPlanRepository = precioPlanRepository;
    }

    public List<PlanClienteDTO> findAll() {
        final List<PlanCliente> planClientes = planClienteRepository.findAll(Sort.by("idPlanCliente"));
        return planClientes.stream()
                .map(planCliente -> mapToDTO(planCliente, new PlanClienteDTO()))
                .toList();
    }

    public PlanClienteDTO get(final Integer idPlanCliente) {
        return planClienteRepository.findById(idPlanCliente)
                .map(planCliente -> mapToDTO(planCliente, new PlanClienteDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Transactional
    public List<PrecioPlanInfoDTO> findAllPlanCliente() {
        return precioPlanRepository.findAll(Sort.by("id"))
                .stream()
                .map(this::mapToPrecioPlanFullDTO)
                .toList();
    }

    private PrecioPlanInfoDTO mapToPrecioPlanFullDTO(PrecioPlan pp) {
        PrecioPlanInfoDTO dto = new PrecioPlanInfoDTO();
        dto.setId(pp.getId());
        dto.setPrecio(pp.getPrecio());

        PlanCliente pc = pp.getPlanCliente();
        PlanClienteDTO pcDto = new PlanClienteDTO();
        pcDto.setIdPlanCliente(pc.getIdPlanCliente());
        pcDto.setNombrePlanCliente(pc.getNombrePlanCliente());
        pcDto.setNumeroWebs(pc.getNumeroWebs());
        pcDto.setNumeroBaseDatos(pc.getNumeroBaseDatos());
        pcDto.setAlmacenamientoNvme(pc.getAlmacenamientoNvme());
        pcDto.setNumeroCuentasCorreo(pc.getNumeroCuentasCorreo());
        pcDto.setCreadorWeb(pc.getCreadorWeb());
        pcDto.setNumeroCertificadoSslHttps(pc.getNumeroCertificadoSslHttps());
        pcDto.setEmailMarketing(pc.getEmailMarketing());
        dto.setPlanCliente(pcDto);

        PlanPago pago = pp.getPlanPago();
        PlanPagoDTO pagoDto = new PlanPagoDTO();
        pagoDto.setIdPlanPago(pago.getIdPlanPago());
        pagoDto.setIntervaloPago(pago.getIntervaloPago());
        dto.setPlanPago(pagoDto);

        return dto;
    }

    public Integer create(final PlanClienteDTO planClienteDTO) {
        final PlanCliente planCliente = new PlanCliente();
        mapToEntity(planClienteDTO, planCliente);
        return planClienteRepository.save(planCliente).getIdPlanCliente();
    }

    public void update(final Integer idPlanCliente, final PlanClienteDTO planClienteDTO) {
        final PlanCliente planCliente = planClienteRepository.findById(idPlanCliente)
                .orElseThrow(NotFoundException::new);
        mapToEntity(planClienteDTO, planCliente);
        planClienteRepository.save(planCliente);
    }

    public void delete(final Integer idPlanCliente) {
        planClienteRepository.deleteById(idPlanCliente);
    }

    private PlanClienteDTO mapToDTO(final PlanCliente planCliente,
            final PlanClienteDTO planClienteDTO) {
        planClienteDTO.setIdPlanCliente(planCliente.getIdPlanCliente());
        planClienteDTO.setNombrePlanCliente(planCliente.getNombrePlanCliente());
        planClienteDTO.setNumeroWebs(planCliente.getNumeroWebs());
        planClienteDTO.setNumeroBaseDatos(planCliente.getNumeroBaseDatos());
        planClienteDTO.setAlmacenamientoNvme(planCliente.getAlmacenamientoNvme());
        planClienteDTO.setNumeroCuentasCorreo(planCliente.getNumeroCuentasCorreo());
        planClienteDTO.setCreadorWeb(planCliente.getCreadorWeb());
        planClienteDTO.setNumeroCertificadoSslHttps(planCliente.getNumeroCertificadoSslHttps());
        planClienteDTO.setEmailMarketing(planCliente.getEmailMarketing());
        return planClienteDTO;
    }

    private PlanCliente mapToEntity(final PlanClienteDTO planClienteDTO,
            final PlanCliente planCliente) {
        planCliente.setNombrePlanCliente(planClienteDTO.getNombrePlanCliente());
        planCliente.setNumeroWebs(planClienteDTO.getNumeroWebs());
        planCliente.setNumeroBaseDatos(planClienteDTO.getNumeroBaseDatos());
        planCliente.setAlmacenamientoNvme(planClienteDTO.getAlmacenamientoNvme());
        planCliente.setNumeroCuentasCorreo(planClienteDTO.getNumeroCuentasCorreo());
        planCliente.setCreadorWeb(planClienteDTO.getCreadorWeb());
        planCliente.setNumeroCertificadoSslHttps(planClienteDTO.getNumeroCertificadoSslHttps());
        planCliente.setEmailMarketing(planClienteDTO.getEmailMarketing());
        return planCliente;
    }

    public ReferencedWarning getReferencedWarning(final Integer idPlanCliente) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final PlanCliente planCliente = planClienteRepository.findById(idPlanCliente)
                .orElseThrow(NotFoundException::new);
        final ClienteDirecto planClienteClienteDirecto = clienteDirectoRepository.findFirstByPlanCliente(planCliente);
        if (planClienteClienteDirecto != null) {
            referencedWarning.setKey("planCliente.clienteDirecto.planCliente.referenced");
            referencedWarning.addParam(planClienteClienteDirecto.getIdCliente());
            return referencedWarning;
        }
        final PlanAdquirido planClientePlanAdquirido = planAdquiridoRepository.findFirstByPlanCliente(planCliente);
        if (planClientePlanAdquirido != null) {
            referencedWarning.setKey("planCliente.planAdquirido.planCliente.referenced");
            referencedWarning.addParam(planClientePlanAdquirido.getIdPlanAdquirido());
            return referencedWarning;
        }
        final PrecioPlan planClientePrecioPlan = precioPlanRepository.findFirstByPlanCliente(planCliente);
        if (planClientePrecioPlan != null) {
            referencedWarning.setKey("planCliente.precioPlan.planCliente.referenced");
            referencedWarning.addParam(planClientePrecioPlan.getId());
            return referencedWarning;
        }
        return null;
    }
}