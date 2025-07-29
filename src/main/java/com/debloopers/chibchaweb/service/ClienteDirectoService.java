package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.entity.*;
import com.debloopers.chibchaweb.dto.*;
import com.debloopers.chibchaweb.repository.*;
import com.debloopers.chibchaweb.util.NotFoundException;
import com.debloopers.chibchaweb.util.ReferencedWarning;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ClienteDirectoService {

    private final ClienteDirectoRepository clienteDirectoRepository;
    private final PlanRepository planRepository;
    private final UsuarioRepository usuarioRepository;
    private final TicketRepository ticketRepository;
    private final SolicitudDominioRepository solicitudDominioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ClienteDirectoService(final ClienteDirectoRepository clienteDirectoRepository,
                                 final PlanRepository planRepository, final UsuarioRepository usuarioRepository,
                                 final TicketRepository ticketRepository,
                                 final SolicitudDominioRepository solicitudDominioRepository) {
        this.clienteDirectoRepository = clienteDirectoRepository;
        this.planRepository = planRepository;
        this.usuarioRepository = usuarioRepository;
        this.ticketRepository = ticketRepository;
        this.solicitudDominioRepository = solicitudDominioRepository;
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
    public ClienteDirectoRegistroResponseDTO create(ClienteDirectoRegistroRequestDTO dto) {
        try {
            if (usuarioRepository.findByCorreoUsuario(dto.getCorreoCliente()) != null) {
                return new ClienteDirectoRegistroResponseDTO(false, "The email is already registered.");
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
            usuario.setEstado("ACTIVO");
            usuario.setCliente(cliente);
            usuarioRepository.save(usuario);

            return new ClienteDirectoRegistroResponseDTO(true, "Customer successfully created.");
        } catch (Exception e) {
            return new ClienteDirectoRegistroResponseDTO(false, "Internal error when creating the client.");
        }
    }

    public void update(final Integer idCliente, final ClienteDirectoActualizarDTO clienteDirectoDTO) {
        final ClienteDirecto cliente = clienteDirectoRepository.findById(idCliente)
                .orElseThrow(NotFoundException::new);

        if (clienteDirectoDTO.getNombreCliente() != null && !clienteDirectoDTO.getNombreCliente().isBlank()) {
            cliente.setNombreCliente(clienteDirectoDTO.getNombreCliente());
        }
        if (clienteDirectoDTO.getApellidoCliente() != null && !clienteDirectoDTO.getApellidoCliente().isBlank()) {
            cliente.setApellidoCliente(clienteDirectoDTO.getApellidoCliente());
        }
        if (clienteDirectoDTO.getTelefono() != null && !clienteDirectoDTO.getTelefono().isBlank()) {
            cliente.setTelefono(clienteDirectoDTO.getTelefono());
        }
        if (clienteDirectoDTO.getFechaNacimientoCliente() != null) {
            cliente.setFechaNacimientoCliente(clienteDirectoDTO.getFechaNacimientoCliente());
        }
        if (clienteDirectoDTO.getPlan() != null) {
            Plan plan = planRepository.findById(clienteDirectoDTO.getPlan())
                    .orElseThrow(() -> new NotFoundException("Plan not found"));
            cliente.setPlan(plan);
        }

        clienteDirectoRepository.save(cliente);
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
            dto.setPlan(cliente.getPlan() != null ? cliente.getPlan().getIdPlan() : null);
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
        clienteDirectoDTO.setPlan(clienteDirecto.getPlan() == null ? null : clienteDirecto.getPlan().getIdPlan());
        return clienteDirectoDTO;
    }

    private ClienteDirecto mapToEntity(final ClienteDirectoDTO clienteDirectoDTO,
            final ClienteDirecto clienteDirecto) {
        clienteDirecto.setNombreCliente(clienteDirectoDTO.getNombreCliente());
        clienteDirecto.setApellidoCliente(clienteDirectoDTO.getApellidoCliente());
        clienteDirecto.setTelefono(clienteDirectoDTO.getTelefono());
        clienteDirecto.setFechaNacimientoCliente(clienteDirectoDTO.getFechaNacimientoCliente());
        final Plan plan = clienteDirectoDTO.getPlan() == null ? null : planRepository.findById(clienteDirectoDTO.getPlan())
                .orElseThrow(() -> new NotFoundException("plan not found"));
        clienteDirecto.setPlan(plan);
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
        return null;
    }

}
