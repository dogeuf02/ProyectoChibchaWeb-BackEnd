package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.domain.ClienteDirecto;
import com.debloopers.chibchaweb.domain.Plan;
import com.debloopers.chibchaweb.domain.SolicitudDomCd;
import com.debloopers.chibchaweb.domain.Ticket;
import com.debloopers.chibchaweb.domain.Usuario;
import com.debloopers.chibchaweb.model.ClienteDirectoDTO;
import com.debloopers.chibchaweb.repos.ClienteDirectoRepository;
import com.debloopers.chibchaweb.repos.PlanRepository;
import com.debloopers.chibchaweb.repos.SolicitudDomCdRepository;
import com.debloopers.chibchaweb.repos.TicketRepository;
import com.debloopers.chibchaweb.repos.UsuarioRepository;
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
    private final SolicitudDomCdRepository solicitudDomCdRepository;
    private final TicketRepository ticketRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ClienteDirectoService(final ClienteDirectoRepository clienteDirectoRepository,
            final PlanRepository planRepository,
            final SolicitudDomCdRepository solicitudDomCdRepository,
            final TicketRepository ticketRepository, final UsuarioRepository usuarioRepository) {
        this.clienteDirectoRepository = clienteDirectoRepository;
        this.planRepository = planRepository;
        this.solicitudDomCdRepository = solicitudDomCdRepository;
        this.ticketRepository = ticketRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<ClienteDirectoDTO> findAll() {
        final List<ClienteDirecto> clienteDirectoes = clienteDirectoRepository.findAll(Sort.by("idCliente"));
        return clienteDirectoes.stream()
                .map(clienteDirecto -> mapToDTO(clienteDirecto, new ClienteDirectoDTO()))
                .toList();
    }

    public ClienteDirectoDTO get(final String idCliente) {
        return clienteDirectoRepository.findById(idCliente)
                .map(clienteDirecto -> mapToDTO(clienteDirecto, new ClienteDirectoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Transactional
    public boolean create(ClienteDirectoDTO dto) {
        try {
            // Verificar si el correo ya existe
            if (usuarioRepository.findByCorreoUsuario(dto.getCorreoCliente()) != null) {
                return false; // Correo ya registrado, no continuar
            }

            Usuario usuario = new Usuario();
            usuario.setCorreoUsuario(dto.getCorreoCliente());
            usuario.setContrasena(passwordEncoder.encode(dto.getContrasenaCliente()));
            usuario.setRolUsuario("Cliente");
            usuario = usuarioRepository.save(usuario);

            ClienteDirecto cliente = new ClienteDirecto();
            cliente.setIdCliente(usuario.getIdUsuario().toString());
            cliente.setNombreCliente(dto.getNombreCliente());
            cliente.setApellidoCliente(dto.getApellidoCliente());
            cliente.setTelefono(dto.getTelefono());
            cliente.setFechaNacimientoCliente(dto.getFechaNacimientoCliente());
            // cliente.setIdPlan(dto.getIdPlan());
            clienteDirectoRepository.save(cliente);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void update(final String idCliente, final ClienteDirectoDTO clienteDirectoDTO) {
        final ClienteDirecto clienteDirecto = clienteDirectoRepository.findById(idCliente)
                .orElseThrow(NotFoundException::new);
        mapToEntity(clienteDirectoDTO, clienteDirecto);
        clienteDirectoRepository.save(clienteDirecto);
    }

    public void delete(final String idCliente) {
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
        if (clienteDirectoDTO.getNombreCliente() != null) {
            clienteDirecto.setNombreCliente(clienteDirectoDTO.getNombreCliente());
        }
        if (clienteDirectoDTO.getApellidoCliente() != null) {
            clienteDirecto.setApellidoCliente(clienteDirectoDTO.getApellidoCliente());
        }
        if (clienteDirectoDTO.getTelefono() != null) {
            clienteDirecto.setTelefono(clienteDirectoDTO.getTelefono());
        }
        if (clienteDirectoDTO.getFechaNacimientoCliente() != null) {
            clienteDirecto.setFechaNacimientoCliente(clienteDirectoDTO.getFechaNacimientoCliente());
        }
        if (clienteDirectoDTO.getPlan() != null) {
            final Plan plan = planRepository.findById(clienteDirectoDTO.getPlan())
                    .orElseThrow(() -> new NotFoundException("plan not found"));
            clienteDirecto.setPlan(plan);
        }
        return clienteDirecto;
    }


    public boolean idClienteExists(final String idCliente) {
        return clienteDirectoRepository.existsByIdClienteIgnoreCase(idCliente);
    }

    public ReferencedWarning getReferencedWarning(final String idCliente) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final ClienteDirecto clienteDirecto = clienteDirectoRepository.findById(idCliente)
                .orElseThrow(NotFoundException::new);
        final SolicitudDomCd clienteSolicitudDomCd = solicitudDomCdRepository.findFirstByCliente(clienteDirecto);
        if (clienteSolicitudDomCd != null) {
            referencedWarning.setKey("clienteDirecto.solicitudDomCd.cliente.referenced");
            referencedWarning.addParam(clienteSolicitudDomCd.getTld());
            return referencedWarning;
        }
        final Ticket clienteTicket = ticketRepository.findFirstByCliente(clienteDirecto);
        if (clienteTicket != null) {
            referencedWarning.setKey("clienteDirecto.ticket.cliente.referenced");
            referencedWarning.addParam(clienteTicket.getIdTicket());
            return referencedWarning;
        }
        final Usuario clienteUsuario = usuarioRepository.findFirstByCliente(clienteDirecto);
        if (clienteUsuario != null) {
            referencedWarning.setKey("clienteDirecto.usuario.cliente.referenced");
            referencedWarning.addParam(clienteUsuario.getIdUsuario());
            return referencedWarning;
        }
        return null;
    }

}
