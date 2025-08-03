package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.dto.MedioPagoDTO;
import com.debloopers.chibchaweb.entity.*;
import com.debloopers.chibchaweb.repository.*;
import com.debloopers.chibchaweb.util.NotFoundException;
import com.debloopers.chibchaweb.util.ReferencedWarning;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class MedioPagoService {

    private final MedioPagoRepository medioPagoRepository;
    private final ClienteDirectoRepository clienteDirectoRepository;
    private final DistribuidorRepository distribuidorRepository;
    private final BancoRepository bancoRepository;
    private final FacturaRepository facturaRepository;
    private final ComisionRepository comisionRepository;

    public MedioPagoService(final MedioPagoRepository medioPagoRepository,
            final ClienteDirectoRepository clienteDirectoRepository,
            final DistribuidorRepository distribuidorRepository,
            final BancoRepository bancoRepository, final FacturaRepository facturaRepository,
            final ComisionRepository comisionRepository) {
        this.medioPagoRepository = medioPagoRepository;
        this.clienteDirectoRepository = clienteDirectoRepository;
        this.distribuidorRepository = distribuidorRepository;
        this.bancoRepository = bancoRepository;
        this.facturaRepository = facturaRepository;
        this.comisionRepository = comisionRepository;
    }

    public List<MedioPagoDTO> findAll() {
        final List<MedioPago> medioPagoes = medioPagoRepository.findAll(Sort.by("idMedioPago"));
        return medioPagoes.stream()
                .map(medioPago -> mapToDTO(medioPago, new MedioPagoDTO()))
                .toList();
    }

    public MedioPagoDTO get(final Integer idMedioPago) {
        return medioPagoRepository.findById(idMedioPago)
                .map(medioPago -> mapToDTO(medioPago, new MedioPagoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public List<MedioPagoDTO> findAllByCliente(Integer idCliente) {
        ClienteDirecto cliente = clienteDirectoRepository.findById(idCliente)
                .orElseThrow(() -> new NotFoundException("cliente not found"));

        List<MedioPago> medios = medioPagoRepository.findByCliente(cliente);

        return medios.stream()
                .map(medio -> mapToDTO(medio, new MedioPagoDTO()))
                .toList();
    }

    public List<MedioPagoDTO> findAllByDistribuidor(Integer idDistribuidor) {
        Distribuidor distribuidor = distribuidorRepository.findById(idDistribuidor)
                .orElseThrow(() -> new NotFoundException("distribuidor not found"));

        List<MedioPago> medios = medioPagoRepository.findByDistribuidor(distribuidor);

        return medios.stream()
                .map(medio -> mapToDTO(medio, new MedioPagoDTO()))
                .toList();
    }

    public Integer create(final MedioPagoDTO medioPagoDTO) {
        final MedioPago medioPago = new MedioPago();
        mapToEntity(medioPagoDTO, medioPago);
        return medioPagoRepository.save(medioPago).getIdMedioPago();
    }

    public void update(final Integer idMedioPago, final MedioPagoDTO medioPagoDTO) {
        final MedioPago medioPago = medioPagoRepository.findById(idMedioPago)
                .orElseThrow(NotFoundException::new);
        mapToEntity(medioPagoDTO, medioPago);
        medioPagoRepository.save(medioPago);
    }

    public void delete(final Integer idMedioPago) {
        medioPagoRepository.deleteById(idMedioPago);
    }

    private MedioPagoDTO mapToDTO(final MedioPago medioPago, final MedioPagoDTO medioPagoDTO) {
        medioPagoDTO.setIdMedioPago(medioPago.getIdMedioPago());
        medioPagoDTO.setTipoMedioPago(medioPago.getTipoMedioPago());
        medioPagoDTO.setNombreTitular(medioPago.getNombreTitular());
        medioPagoDTO.setNumeroTarjetaCuenta(medioPago.getNumeroTarjetaCuenta());
        medioPagoDTO.setCorreoPse(medioPago.getCorreoPse());
        medioPagoDTO.setFechaRegistro(medioPago.getFechaRegistro());
        medioPagoDTO.setCliente(medioPago.getCliente() == null ? null : medioPago.getCliente().getIdCliente());
        medioPagoDTO.setDistribuidor(medioPago.getDistribuidor() == null ? null : medioPago.getDistribuidor().getIdDistribuidor());
        medioPagoDTO.setBanco(medioPago.getBanco() == null ? null : medioPago.getBanco().getIdBanco());
        return medioPagoDTO;
    }

    private MedioPago mapToEntity(final MedioPagoDTO medioPagoDTO, final MedioPago medioPago) {
        medioPago.setTipoMedioPago(medioPagoDTO.getTipoMedioPago());
        medioPago.setNombreTitular(medioPagoDTO.getNombreTitular());
        medioPago.setNumeroTarjetaCuenta(medioPagoDTO.getNumeroTarjetaCuenta());
        medioPago.setCorreoPse(medioPagoDTO.getCorreoPse());
        medioPago.setFechaRegistro(medioPagoDTO.getFechaRegistro());
        final ClienteDirecto cliente = medioPagoDTO.getCliente() == null ? null : clienteDirectoRepository.findById(medioPagoDTO.getCliente())
                .orElseThrow(() -> new NotFoundException("cliente not found"));
        medioPago.setCliente(cliente);
        final Distribuidor distribuidor = medioPagoDTO.getDistribuidor() == null ? null : distribuidorRepository.findById(medioPagoDTO.getDistribuidor())
                .orElseThrow(() -> new NotFoundException("distribuidor not found"));
        medioPago.setDistribuidor(distribuidor);
        final Banco banco = medioPagoDTO.getBanco() == null ? null : bancoRepository.findById(medioPagoDTO.getBanco())
                .orElseThrow(() -> new NotFoundException("banco not found"));
        medioPago.setBanco(banco);
        return medioPago;
    }

    public ReferencedWarning getReferencedWarning(final Integer idMedioPago) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final MedioPago medioPago = medioPagoRepository.findById(idMedioPago)
                .orElseThrow(NotFoundException::new);
        final Factura medioPagoFactura = facturaRepository.findFirstByMedioPago(medioPago);
        if (medioPagoFactura != null) {
            referencedWarning.setKey("medioPago.factura.medioPago.referenced");
            referencedWarning.addParam(medioPagoFactura.getIdFactura());
            return referencedWarning;
        }
        final Comision medioPagoComision = comisionRepository.findFirstByMedioPago(medioPago);
        if (medioPagoComision != null) {
            referencedWarning.setKey("medioPago.comision.medioPago.referenced");
            referencedWarning.addParam(medioPagoComision.getIdComision());
            return referencedWarning;
        }
        return null;
    }
}