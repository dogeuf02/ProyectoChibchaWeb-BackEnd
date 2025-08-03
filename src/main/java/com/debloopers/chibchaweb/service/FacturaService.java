package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.dto.FacturaDTO;
import com.debloopers.chibchaweb.entity.Factura;
import com.debloopers.chibchaweb.entity.MedioPago;
import com.debloopers.chibchaweb.entity.PlanAdquirido;
import com.debloopers.chibchaweb.repository.FacturaRepository;
import com.debloopers.chibchaweb.repository.MedioPagoRepository;
import com.debloopers.chibchaweb.repository.PlanAdquiridoRepository;
import com.debloopers.chibchaweb.util.NotFoundException;
import com.debloopers.chibchaweb.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class FacturaService {

    private final FacturaRepository facturaRepository;
    private final PlanAdquiridoRepository planAdquiridoRepository;
    private final MedioPagoRepository medioPagoRepository;

    public FacturaService(final FacturaRepository facturaRepository,
            final PlanAdquiridoRepository planAdquiridoRepository,
            final MedioPagoRepository medioPagoRepository) {
        this.facturaRepository = facturaRepository;
        this.planAdquiridoRepository = planAdquiridoRepository;
        this.medioPagoRepository = medioPagoRepository;
    }

    public List<FacturaDTO> findAll() {
        final List<Factura> facturas = facturaRepository.findAll(Sort.by("idFactura"));
        return facturas.stream()
                .map(factura -> mapToDTO(factura, new FacturaDTO()))
                .toList();
    }

    public FacturaDTO get(final Integer idFactura) {
        return facturaRepository.findById(idFactura)
                .map(factura -> mapToDTO(factura, new FacturaDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final FacturaDTO facturaDTO) {
        final Factura factura = new Factura();
        mapToEntity(facturaDTO, factura);
        return facturaRepository.save(factura).getIdFactura();
    }

    public void update(final Integer idFactura, final FacturaDTO facturaDTO) {
        final Factura factura = facturaRepository.findById(idFactura)
                .orElseThrow(NotFoundException::new);
        mapToEntity(facturaDTO, factura);
        facturaRepository.save(factura);
    }

    public void delete(final Integer idFactura) {
        facturaRepository.deleteById(idFactura);
    }

    private FacturaDTO mapToDTO(final Factura factura, final FacturaDTO facturaDTO) {
        facturaDTO.setIdFactura(factura.getIdFactura());
        facturaDTO.setFechaFacturacion(factura.getFechaFacturacion());
        facturaDTO.setTotal(factura.getTotal());
        facturaDTO.setEstadoPago(factura.getEstadoPago());
        facturaDTO.setPlanAdquirido(factura.getPlanAdquirido() == null ? null : factura.getPlanAdquirido().getIdPlanAdquirido());
        facturaDTO.setMedioPago(factura.getMedioPago() == null ? null : factura.getMedioPago().getIdMedioPago());
        return facturaDTO;
    }

    private Factura mapToEntity(final FacturaDTO facturaDTO, final Factura factura) {
        factura.setFechaFacturacion(facturaDTO.getFechaFacturacion());
        factura.setTotal(facturaDTO.getTotal());
        factura.setEstadoPago(facturaDTO.getEstadoPago());
        final PlanAdquirido planAdquirido = facturaDTO.getPlanAdquirido() == null ? null : planAdquiridoRepository.findById(facturaDTO.getPlanAdquirido())
                .orElseThrow(() -> new NotFoundException("planAdquirido not found"));
        factura.setPlanAdquirido(planAdquirido);
        final MedioPago medioPago = facturaDTO.getMedioPago() == null ? null : medioPagoRepository.findById(facturaDTO.getMedioPago())
                .orElseThrow(() -> new NotFoundException("medioPago not found"));
        factura.setMedioPago(medioPago);
        return factura;
    }
}