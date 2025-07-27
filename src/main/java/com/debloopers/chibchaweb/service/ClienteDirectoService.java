package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.domain.ClienteDirecto;
import com.debloopers.chibchaweb.domain.Plan;
import com.debloopers.chibchaweb.domain.SolicitudDomCliente;
import com.debloopers.chibchaweb.domain.Ticket;
import com.debloopers.chibchaweb.domain.Usuario;
import com.debloopers.chibchaweb.model.ClienteDirectoDTO;
import com.debloopers.chibchaweb.repos.ClienteDirectoRepository;
import com.debloopers.chibchaweb.repos.PlanRepository;
import com.debloopers.chibchaweb.repos.SolicitudDomClienteRepository;
import com.debloopers.chibchaweb.repos.TicketRepository;
import com.debloopers.chibchaweb.repos.UsuarioRepository;
import com.debloopers.chibchaweb.util.NotFoundException;
import com.debloopers.chibchaweb.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ClienteDirectoService {

    private final ClienteDirectoRepository clienteDirectoRepository;
    private final PlanRepository planRepository;
    private final UsuarioRepository usuarioRepository;
    private final SolicitudDomClienteRepository solicitudDomClienteRepository;
    private final TicketRepository ticketRepository;

    public ClienteDirectoService(final ClienteDirectoRepository clienteDirectoRepository,
            final PlanRepository planRepository, final UsuarioRepository usuarioRepository,
            final SolicitudDomClienteRepository solicitudDomClienteRepository,
            final TicketRepository ticketRepository) {
        this.clienteDirectoRepository = clienteDirectoRepository;
        this.planRepository = planRepository;
        this.usuarioRepository = usuarioRepository;
        this.solicitudDomClienteRepository = solicitudDomClienteRepository;
        this.ticketRepository = ticketRepository;
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

    public Integer create(final ClienteDirectoDTO clienteDirectoDTO) {
        final ClienteDirecto clienteDirecto = new ClienteDirecto();
        mapToEntity(clienteDirectoDTO, clienteDirecto);
        return clienteDirectoRepository.save(clienteDirecto).getIdCliente();
    }

    public void update(final Integer idCliente, final ClienteDirectoDTO clienteDirectoDTO) {
        final ClienteDirecto clienteDirecto = clienteDirectoRepository.findById(idCliente)
                .orElseThrow(NotFoundException::new);
        mapToEntity(clienteDirectoDTO, clienteDirecto);
        clienteDirectoRepository.save(clienteDirecto);
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
        final SolicitudDomCliente clienteSolicitudDomCliente = solicitudDomClienteRepository.findFirstByCliente(clienteDirecto);
        if (clienteSolicitudDomCliente != null) {
            referencedWarning.setKey("clienteDirecto.solicitudDomCliente.cliente.referenced");
            referencedWarning.addParam(clienteSolicitudDomCliente.getTld());
            return referencedWarning;
        }
        final Ticket clienteTicket = ticketRepository.findFirstByCliente(clienteDirecto);
        if (clienteTicket != null) {
            referencedWarning.setKey("clienteDirecto.ticket.cliente.referenced");
            referencedWarning.addParam(clienteTicket.getIdTicket());
            return referencedWarning;
        }
        return null;
    }

}
