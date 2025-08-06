package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.dto.PlanAdquiridoDTO;
import com.debloopers.chibchaweb.entity.*;
import com.debloopers.chibchaweb.repository.*;
import com.debloopers.chibchaweb.util.NotFoundException;
import com.debloopers.chibchaweb.util.ReferencedWarning;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class PlanAdquiridoService {

    private final PlanAdquiridoRepository planAdquiridoRepository;
    private final ClienteDirectoRepository clienteDirectoRepository;
    private final PlanClienteRepository planClienteRepository;
    private final PlanPagoRepository planPagoRepository;
    private final FacturaRepository facturaRepository;

    public PlanAdquiridoService(final PlanAdquiridoRepository planAdquiridoRepository,
            final ClienteDirectoRepository clienteDirectoRepository,
            final PlanClienteRepository planClienteRepository,
            final PlanPagoRepository planPagoRepository,
            final FacturaRepository facturaRepository) {
        this.planAdquiridoRepository = planAdquiridoRepository;
        this.clienteDirectoRepository = clienteDirectoRepository;
        this.planClienteRepository = planClienteRepository;
        this.planPagoRepository = planPagoRepository;
        this.facturaRepository = facturaRepository;
    }

    public List<PlanAdquiridoDTO> findAll() {
        final List<PlanAdquirido> planAdquiridoes = planAdquiridoRepository.findAll(Sort.by("idPlanAdquirido"));
        return planAdquiridoes.stream()
                .map(planAdquirido -> mapToDTO(planAdquirido, new PlanAdquiridoDTO()))
                .toList();
    }

    public PlanAdquiridoDTO get(final Integer idPlanAdquirido) {
        return planAdquiridoRepository.findById(idPlanAdquirido)
                .map(planAdquirido -> mapToDTO(planAdquirido, new PlanAdquiridoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Transactional
    public List<PlanAdquiridoDTO> findByClienteId(final Integer idCliente) {
        final ClienteDirecto cliente = clienteDirectoRepository.findById(idCliente)
                .orElseThrow(() -> new NotFoundException("Cliente con ID " + idCliente + " no encontrado"));

        final List<PlanAdquirido> planes = planAdquiridoRepository.findByCliente(cliente);

        return planes.stream()
                .map(plan -> mapToDTO(plan, new PlanAdquiridoDTO()))
                .toList();
    }

    public Integer create(final PlanAdquiridoDTO planAdquiridoDTO) {
        final PlanAdquirido planAdquirido = new PlanAdquirido();
        mapToEntity(planAdquiridoDTO, planAdquirido);
        return planAdquiridoRepository.save(planAdquirido).getIdPlanAdquirido();
    }

    public void update(final Integer idPlanAdquirido, final PlanAdquiridoDTO planAdquiridoDTO) {
        final PlanAdquirido planAdquirido = planAdquiridoRepository.findById(idPlanAdquirido)
                .orElseThrow(NotFoundException::new);
        mapToEntity(planAdquiridoDTO, planAdquirido);
        planAdquiridoRepository.save(planAdquirido);
    }

    public void delete(final Integer idPlanAdquirido) {
        planAdquiridoRepository.deleteById(idPlanAdquirido);
    }

    private PlanAdquiridoDTO mapToDTO(final PlanAdquirido planAdquirido,
            final PlanAdquiridoDTO planAdquiridoDTO) {
        planAdquiridoDTO.setIdPlanAdquirido(planAdquirido.getIdPlanAdquirido());
        planAdquiridoDTO.setEstadoPlan(planAdquirido.getEstadoPlan());
        planAdquiridoDTO.setFechaCompra(planAdquirido.getFechaCompra());
        planAdquiridoDTO.setFechaExpiracion(planAdquirido.getFechaExpiracion());
        planAdquiridoDTO.setFechaActualizacion(planAdquirido.getFechaActualizacion());
        planAdquiridoDTO.setPrecioPlanAdquirido(planAdquirido.getPrecioPlanAdquirido());
        planAdquiridoDTO.setCliente(planAdquirido.getCliente() == null ? null : planAdquirido.getCliente().getIdCliente());
        planAdquiridoDTO.setPlanCliente(planAdquirido.getPlanCliente() == null ? null : planAdquirido.getPlanCliente().getIdPlanCliente());
        planAdquiridoDTO.setPlanPago(planAdquirido.getPlanPago() == null ? null : planAdquirido.getPlanPago().getIdPlanPago());
        return planAdquiridoDTO;
    }

    private PlanAdquirido mapToEntity(final PlanAdquiridoDTO planAdquiridoDTO,
            final PlanAdquirido planAdquirido) {
        planAdquirido.setEstadoPlan(planAdquiridoDTO.getEstadoPlan());
        planAdquirido.setFechaCompra(planAdquiridoDTO.getFechaCompra());
        planAdquirido.setFechaExpiracion(planAdquiridoDTO.getFechaExpiracion());
        planAdquirido.setFechaActualizacion(planAdquiridoDTO.getFechaActualizacion());
        planAdquirido.setPrecioPlanAdquirido(planAdquiridoDTO.getPrecioPlanAdquirido());
        final ClienteDirecto cliente = planAdquiridoDTO.getCliente() == null ? null : clienteDirectoRepository.findById(planAdquiridoDTO.getCliente())
                .orElseThrow(() -> new NotFoundException("cliente not found"));
        planAdquirido.setCliente(cliente);
        final PlanCliente planCliente = planAdquiridoDTO.getPlanCliente() == null ? null : planClienteRepository.findById(planAdquiridoDTO.getPlanCliente())
                .orElseThrow(() -> new NotFoundException("planCliente not found"));
        planAdquirido.setPlanCliente(planCliente);
        final PlanPago planPago = planAdquiridoDTO.getPlanPago() == null ? null : planPagoRepository.findById(planAdquiridoDTO.getPlanPago())
                .orElseThrow(() -> new NotFoundException("planPago not found"));
        planAdquirido.setPlanPago(planPago);
        return planAdquirido;
    }

    public ReferencedWarning getReferencedWarning(final Integer idPlanAdquirido) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final PlanAdquirido planAdquirido = planAdquiridoRepository.findById(idPlanAdquirido)
                .orElseThrow(NotFoundException::new);
        final Factura planAdquiridoFactura = facturaRepository.findFirstByPlanAdquirido(planAdquirido);
        if (planAdquiridoFactura != null) {
            referencedWarning.setKey("planAdquirido.factura.planAdquirido.referenced");
            referencedWarning.addParam(planAdquiridoFactura.getIdFactura());
            return referencedWarning;
        }
        return null;
    }
}