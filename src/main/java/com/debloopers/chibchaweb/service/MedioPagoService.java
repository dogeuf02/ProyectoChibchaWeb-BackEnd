package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.dto.MedioPagoDTO;
import com.debloopers.chibchaweb.entity.*;
import com.debloopers.chibchaweb.repository.*;
import com.debloopers.chibchaweb.util.EncryptionUtils;
import com.debloopers.chibchaweb.util.NotFoundException;
import com.debloopers.chibchaweb.util.ReferencedWarning;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


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

    @Transactional
    public Integer create(final MedioPagoDTO medioPagoDTO) {
        String tipo = medioPagoDTO.getTipoMedioPago();
        if (tipo == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payment type is required");
        }

        boolean isCard = "Credito".equalsIgnoreCase(tipo) || "Debito".equalsIgnoreCase(tipo);
        if (isCard) {
            String raw = medioPagoDTO.getNumeroTarjetaCuenta();
            if (raw == null || raw.isBlank()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Card number is required");
            }
            if (!isValidCardNumber(raw)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid card number");
            }

            String clean = raw.replaceAll("\\D", "");
            medioPagoDTO.setNumeroTarjetaCuenta(clean);

        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported payment type");
        }

        MedioPago medioPago = mapToEntity(medioPagoDTO, new MedioPago());

        if (medioPago.getBanco() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bank is required");
        }
        if (medioPago.getNombreTitular() == null || medioPago.getNombreTitular().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cardholder name is required");
        }

        MedioPago saved = medioPagoRepository.save(medioPago);
        return saved.getIdMedioPago();
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

        if (medioPago.getNumeroTarjetaCuenta() != null) {
            try {
                String decrypted = EncryptionUtils.decrypt(medioPago.getNumeroTarjetaCuenta());
                medioPagoDTO.setNumeroTarjetaCuenta(EncryptionUtils.maskCard(decrypted));
            } catch (Exception e) {
                medioPagoDTO.setNumeroTarjetaCuenta("****");
            }
        }

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

        if (medioPagoDTO.getNumeroTarjetaCuenta() != null) {
            medioPago.setNumeroTarjetaCuenta(
                    EncryptionUtils.encrypt(medioPagoDTO.getNumeroTarjetaCuenta())
            );
        }

        medioPago.setCorreoPse(medioPagoDTO.getCorreoPse());
        medioPago.setFechaRegistro(medioPagoDTO.getFechaRegistro());

        final ClienteDirecto cliente = medioPagoDTO.getCliente() == null ? null :
                clienteDirectoRepository.findById(medioPagoDTO.getCliente())
                        .orElseThrow(() -> new NotFoundException("cliente not found"));
        medioPago.setCliente(cliente);

        final Distribuidor distribuidor = medioPagoDTO.getDistribuidor() == null ? null :
                distribuidorRepository.findById(medioPagoDTO.getDistribuidor())
                        .orElseThrow(() -> new NotFoundException("distribuidor not found"));
        medioPago.setDistribuidor(distribuidor);

        final Banco banco = medioPagoDTO.getBanco() == null ? null :
                bancoRepository.findById(medioPagoDTO.getBanco())
                        .orElseThrow(() -> new NotFoundException("banco not found"));
        medioPago.setBanco(banco);

        return medioPago;
    }

    private boolean isValidCardNumber(String raw) {
        if (raw == null) return false;
        String n = raw.replaceAll("\\D", "");

        if (n.length() < 13 || n.length() > 19) return false;

        int sum = 0;
        boolean alternate = false;
        for (int i = n.length() - 1; i >= 0; i--) {
            int digit = n.charAt(i) - '0';
            if (digit < 0 || digit > 9) return false;

            if (alternate) {
                digit *= 2;
                if (digit > 9) digit -= 9;
            }
            sum += digit;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
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