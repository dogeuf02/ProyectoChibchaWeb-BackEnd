package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.entity.*;
import com.debloopers.chibchaweb.dto.*;
import com.debloopers.chibchaweb.repository.*;
import com.debloopers.chibchaweb.util.NotFoundException;
import com.debloopers.chibchaweb.util.ReferencedWarning;

import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class DistribuidorService {

    private final DistribuidorRepository distribuidorRepository;
    private final TipoDocumentoEmpRepository tipoDocumentoEmpRepository;
    private final CategoriaDistribuidorRepository categoriaDistribuidorRepository;
    private final UsuarioRepository usuarioRepository;
    private final TicketRepository ticketRepository;
    private final SolicitudDominioRepository solicitudDominioRepository;
    private final MedioPagoRepository medioPagoRepository;
    private final ComisionRepository comisionRepository;
    private final PerteneceDominioRepository perteneceDominioRepository;
    private final SolicitudTrasladoRepository solicitudTrasladoRepository;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

    private final CaptchaService captchaService;

    public DistribuidorService(final DistribuidorRepository distribuidorRepository,
                               final TipoDocumentoEmpRepository tipoDocumentoEmpRepository,
                               final CategoriaDistribuidorRepository categoriaDistribuidorRepository,
                               final UsuarioRepository usuarioRepository, final TicketRepository ticketRepository,
                               final SolicitudDominioRepository solicitudDominioRepository,
                               final MedioPagoRepository medioPagoRepository,
                               final ComisionRepository comisionRepository,
                               final PerteneceDominioRepository perteneceDominioRepository,
                               final SolicitudTrasladoRepository solicitudTrasladoRepository,
                               final PasswordEncoder passwordEncoder,
                               final EmailService emailService,
                               final CaptchaService captchaService) {
        this.distribuidorRepository = distribuidorRepository;
        this.tipoDocumentoEmpRepository = tipoDocumentoEmpRepository;
        this.categoriaDistribuidorRepository = categoriaDistribuidorRepository;
        this.usuarioRepository = usuarioRepository;
        this.ticketRepository = ticketRepository;
        this.solicitudDominioRepository = solicitudDominioRepository;
        this.medioPagoRepository = medioPagoRepository;
        this.comisionRepository = comisionRepository;
        this.perteneceDominioRepository = perteneceDominioRepository;
        this.solicitudTrasladoRepository = solicitudTrasladoRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.captchaService = captchaService;
    }

    public List<DistribuidorDTO> findAll() {
        final List<Distribuidor> distribuidors = distribuidorRepository.findAll(Sort.by("idDistribuidor"));
        return distribuidors.stream()
                .map(distribuidor -> mapToDTO(distribuidor, new DistribuidorDTO()))
                .toList();
    }

    public DistribuidorDTO get(final Integer idDistribuidor) {
        return distribuidorRepository.findById(idDistribuidor)
                .map(distribuidor -> mapToDTO(distribuidor, new DistribuidorDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Transactional
    public ResponseDTO create(DistribuidorRegistroRequestDTO dto) {

        try {

            boolean captchaOk;
            try {
                captchaOk = captchaService.verifyCaptcha(dto.getCaptchaToken());
            } catch (Exception recaptchaEx) {
                return new ResponseDTO(false, "The captcha could not be verified. Please try again later.");
            }

            if (!captchaOk) {
                return new ResponseDTO(false, "Invalid captcha. Please try again..");
            }

            if (usuarioRepository.findByCorreoUsuario(dto.getCorreoDistrbuidor()) != null) {
                return new ResponseDTO(false, "The email is already registered.");
            }

            TipoDocumentoEmp tipoDoc = tipoDocumentoEmpRepository.findByNombreTipoDoc(dto.getNombreTipoDoc());
            if (tipoDoc == null) {
                return new ResponseDTO(false, "The document type does not exist.");
            }

            Distribuidor distribuidor = new Distribuidor();
            distribuidor.setNumeroDocEmpresa(dto.getNumeroDocEmpresa());
            distribuidor.setNombreEmpresa(dto.getNombreEmpresa());
            distribuidor.setDireccionEmpresa(dto.getDireccionEmpresa());
            distribuidor.setNombreTipoDoc(tipoDoc);
            distribuidorRepository.save(distribuidor);

            Usuario usuario = new Usuario();
            usuario.setCorreoUsuario(dto.getCorreoDistrbuidor());
            usuario.setContrasena(passwordEncoder.encode(dto.getContrasenaDistribuidor()));
            usuario.setRol("Distribuidor");
            usuario.setEstado("PENDIENTE");
            usuario.setDistribuidor(distribuidor);
            usuarioRepository.save(usuario);

            emailService.enviarCorreoSolicitudRegistro(
                    usuario.getCorreoUsuario(),
                    distribuidor.getNombreEmpresa()
            );

            return new ResponseDTO(true, "Distributor successfully created.");
        } catch (Exception e) {
            return new ResponseDTO(false, "Internal error creating the distributor.");
        }
    }

    @Transactional
    public ResponseDTO createSinCaptcha(DistribuidorRegistroSinCaptchaDTO dto) {

        try {
            if (usuarioRepository.findByCorreoUsuario(dto.getCorreoDistrbuidor()) != null) {
                return new ResponseDTO(false, "The email is already registered.");
            }

            TipoDocumentoEmp tipoDoc = tipoDocumentoEmpRepository.findByNombreTipoDoc(dto.getNombreTipoDoc());
            if (tipoDoc == null) {
                return new ResponseDTO(false, "The document type does not exist.");
            }

            Distribuidor distribuidor = new Distribuidor();
            distribuidor.setNumeroDocEmpresa(dto.getNumeroDocEmpresa());
            distribuidor.setNombreEmpresa(dto.getNombreEmpresa());
            distribuidor.setDireccionEmpresa(dto.getDireccionEmpresa());
            distribuidor.setNombreTipoDoc(tipoDoc);
            distribuidorRepository.save(distribuidor);

            Usuario usuario = new Usuario();
            usuario.setCorreoUsuario(dto.getCorreoDistrbuidor());
            usuario.setContrasena(passwordEncoder.encode(dto.getContrasenaDistribuidor()));
            usuario.setRol("Distribuidor");
            usuario.setEstado("ACTIVO");
            usuario.setDistribuidor(distribuidor);
            usuarioRepository.save(usuario);

            return new ResponseDTO(true, "Distributor successfully created.");
        } catch (Exception e) {
            return new ResponseDTO(false, "Internal error creating the distributor.");
        }
    }

    @Transactional
    public void update(final Integer idDistribuidor, final DistribuidorDTO distribuidorDTO) {
        final Distribuidor distribuidor = distribuidorRepository.findById(idDistribuidor)
                .orElseThrow(NotFoundException::new);
        mapToEntity(distribuidorDTO, distribuidor);
        distribuidorRepository.save(distribuidor);
    }

    public List<DistribuidorConCorreoDTO> findAllWithCorreo() {
        List<Distribuidor> distribuidores = distribuidorRepository.findAll(Sort.by("idDistribuidor"));

        return distribuidores.stream().map(distribuidor -> {
            Usuario usuario = usuarioRepository.findFirstByDistribuidor(distribuidor);

            DistribuidorConCorreoDTO dto = new DistribuidorConCorreoDTO();
            dto.setIdDistribuidor(distribuidor.getIdDistribuidor());
            dto.setNumeroDocEmpresa(distribuidor.getNumeroDocEmpresa());
            dto.setNombreEmpresa(distribuidor.getNombreEmpresa());
            dto.setDireccionEmpresa(distribuidor.getDireccionEmpresa());
            dto.setNombreTipoDoc(distribuidor.getNombreTipoDoc() != null ? distribuidor.getNombreTipoDoc().getNombreTipoDoc() : null);
            dto.setCorreo(usuario != null ? usuario.getCorreoUsuario() : null);
            dto.setEstado(usuario != null ? usuario.getEstado() : null);

            return dto;
        }).toList();
    }

    @Transactional
    public void cambiarEstadoDistribuidor(Integer idDistribuidor, boolean activar) {
        Distribuidor distribuidor = distribuidorRepository.findById(idDistribuidor)
                .orElseThrow(() -> new EntityNotFoundException("Distributor not found"));

        Usuario usuario = usuarioRepository.findByDistribuidor_IdDistribuidor(idDistribuidor)
                .orElseThrow(() -> new EntityNotFoundException("Associated user not found"));

        String nuevoEstado = activar ? "ACTIVO" : "INACTIVO";

        if (!nuevoEstado.equals(usuario.getEstado())) {
            usuario.setEstado(nuevoEstado);
            usuarioRepository.save(usuario);

            try {
                emailService.enviarCorreoRespuestaSolicitudRegistro(
                        usuario.getCorreoUsuario(),
                        distribuidor.getNombreEmpresa(),
                        nuevoEstado
                );
            } catch (Exception e) {
                System.err.println("Error sending email to " + usuario.getCorreoUsuario() + ": " + e.getMessage());
            }
        }
    }

    public void delete(final Integer idDistribuidor) {
        distribuidorRepository.deleteById(idDistribuidor);
    }

    private DistribuidorDTO mapToDTO(final Distribuidor distribuidor,
                                     final DistribuidorDTO distribuidorDTO) {
        distribuidorDTO.setIdDistribuidor(distribuidor.getIdDistribuidor());
        distribuidorDTO.setNumeroDocEmpresa(distribuidor.getNumeroDocEmpresa());
        distribuidorDTO.setNombreEmpresa(distribuidor.getNombreEmpresa());
        distribuidorDTO.setDireccionEmpresa(distribuidor.getDireccionEmpresa());
        distribuidorDTO.setNombreTipoDoc(distribuidor.getNombreTipoDoc() == null ? null : distribuidor.getNombreTipoDoc().getNombreTipoDoc());
        distribuidorDTO.setCategoria(distribuidor.getCategoria() == null ? null : distribuidor.getCategoria().getIdCategoria());
        return distribuidorDTO;
    }

    private Distribuidor mapToEntity(final DistribuidorDTO distribuidorDTO,
                                     final Distribuidor distribuidor) {
        distribuidor.setNumeroDocEmpresa(distribuidorDTO.getNumeroDocEmpresa());
        distribuidor.setNombreEmpresa(distribuidorDTO.getNombreEmpresa());
        distribuidor.setDireccionEmpresa(distribuidorDTO.getDireccionEmpresa());
        final TipoDocumentoEmp nombreTipoDoc = distribuidorDTO.getNombreTipoDoc() == null ? null : tipoDocumentoEmpRepository.findById(distribuidorDTO.getNombreTipoDoc())
                .orElseThrow(() -> new NotFoundException("nombreTipoDoc not found"));
        distribuidor.setNombreTipoDoc(nombreTipoDoc);
        final CategoriaDistribuidor categoria = distribuidorDTO.getCategoria() == null ? null : categoriaDistribuidorRepository.findById(distribuidorDTO.getCategoria())
                .orElseThrow(() -> new NotFoundException("categoria not found"));
        distribuidor.setCategoria(categoria);
        return distribuidor;
    }

    public ReferencedWarning getReferencedWarning(final Integer idDistribuidor) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Distribuidor distribuidor = distribuidorRepository.findById(idDistribuidor)
                .orElseThrow(NotFoundException::new);
        final Usuario distribuidorUsuario = usuarioRepository.findFirstByDistribuidor(distribuidor);
        if (distribuidorUsuario != null) {
            referencedWarning.setKey("distribuidor.usuario.distribuidor.referenced");
            referencedWarning.addParam(distribuidorUsuario.getIdUsuario());
            return referencedWarning;
        }
        final Ticket distribuidorTicket = ticketRepository.findFirstByDistribuidor(distribuidor);
        if (distribuidorTicket != null) {
            referencedWarning.setKey("distribuidor.ticket.distribuidor.referenced");
            referencedWarning.addParam(distribuidorTicket.getIdTicket());
            return referencedWarning;
        }
        final SolicitudDominio distribuidorSolicitudDominio = solicitudDominioRepository.findFirstByDistribuidor(distribuidor);
        if (distribuidorSolicitudDominio != null) {
            referencedWarning.setKey("distribuidor.solicitudDominio.distribuidor.referenced");
            referencedWarning.addParam(distribuidorSolicitudDominio.getIdSolicitud());
            return referencedWarning;
        }
        final MedioPago distribuidorMedioPago = medioPagoRepository.findFirstByDistribuidor(distribuidor);
        if (distribuidorMedioPago != null) {
            referencedWarning.setKey("distribuidor.medioPago.distribuidor.referenced");
            referencedWarning.addParam(distribuidorMedioPago.getIdMedioPago());
            return referencedWarning;
        }
        final Comision distribuidorComision = comisionRepository.findFirstByDistribuidor(distribuidor);
        if (distribuidorComision != null) {
            referencedWarning.setKey("distribuidor.comision.distribuidor.referenced");
            referencedWarning.addParam(distribuidorComision.getIdComision());
            return referencedWarning;
        }
        final PerteneceDominio distribuidorPerteneceDominio = perteneceDominioRepository.findFirstByDistribuidor(distribuidor);
        if (distribuidorPerteneceDominio != null) {
            referencedWarning.setKey("distribuidor.perteneceDominio.distribuidor.referenced");
            referencedWarning.addParam(distribuidorPerteneceDominio.getIdPertenece());
            return referencedWarning;
        }
        final SolicitudTraslado distribuidorSolicitudTraslado = solicitudTrasladoRepository.findFirstByDistribuidor(distribuidor);
        if (distribuidorSolicitudTraslado != null) {
            referencedWarning.setKey("distribuidor.solicitudTraslado.distribuidor.referenced");
            referencedWarning.addParam(distribuidorSolicitudTraslado.getIdSolicitudTraslado());
            return referencedWarning;
        }
        return null;
    }
}