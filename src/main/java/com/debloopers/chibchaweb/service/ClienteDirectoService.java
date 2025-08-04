package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.entity.*;
import com.debloopers.chibchaweb.dto.*;
import com.debloopers.chibchaweb.repository.*;
import com.debloopers.chibchaweb.util.NotFoundException;
import com.debloopers.chibchaweb.util.ReferencedWarning;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteDirectoService {

    private final ClienteDirectoRepository clienteDirectoRepository;
    private final PlanClienteRepository planClienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final TicketRepository ticketRepository;
    private final SolicitudDominioRepository solicitudDominioRepository;
    private final MedioPagoRepository medioPagoRepository;
    private final PlanAdquiridoRepository planAdquiridoRepository;
    private final PerteneceDominioRepository perteneceDominioRepository;
    private final SolicitudTrasladoRepository solicitudTrasladoRepository;


    private final PasswordEncoder passwordEncoder;

    private final CaptchaService captchaService;


    private final TokenVerificacionService tokenService;


    private final EmailService emailService;

    public ClienteDirectoService(final ClienteDirectoRepository clienteDirectoRepository,
                                 final PlanClienteRepository planClienteRepository,
                                 final UsuarioRepository usuarioRepository, final TicketRepository ticketRepository,
                                 final SolicitudDominioRepository solicitudDominioRepository,
                                 final MedioPagoRepository medioPagoRepository,
                                 final PlanAdquiridoRepository planAdquiridoRepository,
                                 final PerteneceDominioRepository perteneceDominioRepository,
                                 final SolicitudTrasladoRepository solicitudTrasladoRepository,
                                 final PasswordEncoder passwordEncoder,
                                 final CaptchaService captchaService,
                                 final TokenVerificacionService tokenService,
                                 final EmailService emailService) {
        this.clienteDirectoRepository = clienteDirectoRepository;
        this.planClienteRepository = planClienteRepository;
        this.usuarioRepository = usuarioRepository;
        this.ticketRepository = ticketRepository;
        this.solicitudDominioRepository = solicitudDominioRepository;
        this.medioPagoRepository = medioPagoRepository;
        this.planAdquiridoRepository = planAdquiridoRepository;
        this.perteneceDominioRepository = perteneceDominioRepository;
        this.solicitudTrasladoRepository = solicitudTrasladoRepository;
        this.passwordEncoder = passwordEncoder;
        this.captchaService = captchaService;
        this.tokenService = tokenService;
        this.emailService = emailService;
    }

    public List<ClienteDirectoDTO> findAll() {
        final List<ClienteDirecto> clienteDirectoes = clienteDirectoRepository.findAll(Sort.by("idCliente"));
        return clienteDirectoes.stream()
                .map(clienteDirecto -> mapToDTO(clienteDirecto, new ClienteDirectoDTO()))
                .toList();
    }

    public ClienteDirectoDTO get(final Integer idCliente) {
        return clienteDirectoRepository.findById(idCliente)
                .map(clienteDirecto -> mapToDTO(clienteDirecto, new ClienteDirectoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Transactional
    public ResponseDTO create(ClienteDirectoRegistroRequestDTO dto) {

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

            if (usuarioRepository.findByCorreoUsuario(dto.getCorreoCliente()) != null) {
                return new ResponseDTO(false, "The email is already registered.");
            }

            ClienteDirecto cliente = new ClienteDirecto();
            cliente.setNombreCliente(dto.getNombreCliente());
            cliente.setApellidoCliente(dto.getApellidoCliente());
            cliente.setTelefono(dto.getTelefono());
            cliente.setFechaNacimientoCliente(dto.getFechaNacimientoCliente());
            clienteDirectoRepository.save(cliente);

            Usuario usuario = new Usuario();
            usuario.setCorreoUsuario(dto.getCorreoCliente());
            usuario.setContrasena(passwordEncoder.encode(dto.getContrasenaCliente()));
            usuario.setRol("Cliente");
            usuario.setEstado("PENDIENTE");
            usuario.setCliente(cliente);
            usuarioRepository.save(usuario);

            TokenVerificacion tokenVerificacion = tokenService.crearTokenParaUsuario(usuario);

            emailService.enviarCorreoActivacion(
                    usuario.getCorreoUsuario(),
                    "Activación de cuenta",
                    tokenVerificacion.getToken()
            );

            return new ResponseDTO(true, "Customer registered. Please check your email to activate your account.");
        } catch (Exception e) {
            return new ResponseDTO(false, "Internal error when creating the client.");
        }
    }

    public void update(final Integer idCliente, final ClienteDirectoDTO clienteDirectoDTO) {
        final ClienteDirecto clienteDirecto = clienteDirectoRepository.findById(idCliente)
                .orElseThrow(NotFoundException::new);
        mapToEntity(clienteDirectoDTO, clienteDirecto);
        clienteDirectoRepository.save(clienteDirecto);
    }

    public List<ClienteDirectoConCorreoDTO> findAllWithCorreo() {
        List<ClienteDirecto> clientes = clienteDirectoRepository.findAll(Sort.by("idCliente"));

        return clientes.stream().map(cliente -> {
            Usuario usuario = usuarioRepository.findFirstByCliente(cliente);

            ClienteDirectoConCorreoDTO dto = new ClienteDirectoConCorreoDTO();
            dto.setIdCliente(cliente.getIdCliente());
            dto.setNombreCliente(cliente.getNombreCliente());
            dto.setApellidoCliente(cliente.getApellidoCliente());
            dto.setTelefono(cliente.getTelefono());
            dto.setFechaNacimientoCliente(cliente.getFechaNacimientoCliente());
            dto.setCorreo(usuario != null ? usuario.getCorreoUsuario() : null);
            dto.setEstado(usuario != null ? usuario.getEstado() : null);

            return dto;
        }).toList();
    }

    public void delete(final Integer idCliente) {
        clienteDirectoRepository.deleteById(idCliente);
    }

    private ClienteDirectoDTO mapToDTO(final ClienteDirecto clienteDirecto,
                                       final ClienteDirectoDTO clienteDirectoDTO) {
        clienteDirectoDTO.setIdCliente(clienteDirecto.getIdCliente());
        clienteDirectoDTO.setNombreCliente(clienteDirecto.getNombreCliente());
        clienteDirectoDTO.setApellidoCliente(clienteDirecto.getApellidoCliente());
        clienteDirectoDTO.setTelefono(clienteDirecto.getTelefono());
        clienteDirectoDTO.setFechaNacimientoCliente(clienteDirecto.getFechaNacimientoCliente());
        clienteDirectoDTO.setPlanCliente(clienteDirecto.getPlanCliente() == null ? null : clienteDirecto.getPlanCliente().getIdPlanCliente());
        return clienteDirectoDTO;
    }

    private ClienteDirecto mapToEntity(final ClienteDirectoDTO clienteDirectoDTO,
                                       final ClienteDirecto clienteDirecto) {
        clienteDirecto.setNombreCliente(clienteDirectoDTO.getNombreCliente());
        clienteDirecto.setApellidoCliente(clienteDirectoDTO.getApellidoCliente());
        clienteDirecto.setTelefono(clienteDirectoDTO.getTelefono());
        clienteDirecto.setFechaNacimientoCliente(clienteDirectoDTO.getFechaNacimientoCliente());
        final PlanCliente planCliente = clienteDirectoDTO.getPlanCliente() == null ? null : planClienteRepository.findById(clienteDirectoDTO.getPlanCliente())
                .orElseThrow(() -> new NotFoundException("planCliente not found"));
        clienteDirecto.setPlanCliente(planCliente);
        return clienteDirecto;
    }

    public ReferencedWarning getReferencedWarning(final Integer idCliente) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final ClienteDirecto clienteDirecto = clienteDirectoRepository.findById(idCliente)
                .orElseThrow(NotFoundException::new);
        final Usuario clienteUsuario = usuarioRepository.findFirstByCliente(clienteDirecto);
        if (clienteUsuario != null) {
            referencedWarning.setKey("clienteDirecto.usuario.cliente.referenced");
            referencedWarning.addParam(clienteUsuario.getIdUsuario());
            return referencedWarning;
        }
        final Ticket clienteTicket = ticketRepository.findFirstByCliente(clienteDirecto);
        if (clienteTicket != null) {
            referencedWarning.setKey("clienteDirecto.ticket.cliente.referenced");
            referencedWarning.addParam(clienteTicket.getIdTicket());
            return referencedWarning;
        }
        final SolicitudDominio clienteSolicitudDominio = solicitudDominioRepository.findFirstByCliente(clienteDirecto);
        if (clienteSolicitudDominio != null) {
            referencedWarning.setKey("clienteDirecto.solicitudDominio.cliente.referenced");
            referencedWarning.addParam(clienteSolicitudDominio.getIdSolicitud());
            return referencedWarning;
        }
        final MedioPago clienteMedioPago = medioPagoRepository.findFirstByCliente(clienteDirecto);
        if (clienteMedioPago != null) {
            referencedWarning.setKey("clienteDirecto.medioPago.cliente.referenced");
            referencedWarning.addParam(clienteMedioPago.getIdMedioPago());
            return referencedWarning;
        }
        final PlanAdquirido clientePlanAdquirido = planAdquiridoRepository.findFirstByCliente(clienteDirecto);
        if (clientePlanAdquirido != null) {
            referencedWarning.setKey("clienteDirecto.planAdquirido.cliente.referenced");
            referencedWarning.addParam(clientePlanAdquirido.getIdPlanAdquirido());
            return referencedWarning;
        }
        final PerteneceDominio clientePerteneceDominio = perteneceDominioRepository.findFirstByCliente(clienteDirecto);
        if (clientePerteneceDominio != null) {
            referencedWarning.setKey("clienteDirecto.perteneceDominio.cliente.referenced");
            referencedWarning.addParam(clientePerteneceDominio.getIdPertenece());
            return referencedWarning;
        }
        final SolicitudTraslado clienteSolicitudTraslado = solicitudTrasladoRepository.findFirstByCliente(clienteDirecto);
        if (clienteSolicitudTraslado != null) {
            referencedWarning.setKey("clienteDirecto.solicitudTraslado.cliente.referenced");
            referencedWarning.addParam(clienteSolicitudTraslado.getIdSolicitudTraslado());
            return referencedWarning;
        }
        return null;
    }
}