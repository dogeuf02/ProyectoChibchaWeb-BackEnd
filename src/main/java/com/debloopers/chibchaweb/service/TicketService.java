package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.domain.ClienteDirecto;
import com.debloopers.chibchaweb.domain.Distribuidor;
import com.debloopers.chibchaweb.domain.Empleado;
import com.debloopers.chibchaweb.domain.Ticket;
import com.debloopers.chibchaweb.model.TicketDTO;
import com.debloopers.chibchaweb.repos.ClienteDirectoRepository;
import com.debloopers.chibchaweb.repos.DistribuidorRepository;
import com.debloopers.chibchaweb.repos.EmpleadoRepository;
import com.debloopers.chibchaweb.repos.TicketRepository;
import com.debloopers.chibchaweb.util.NotFoundException;
import java.util.HashSet;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(rollbackFor = Exception.class)
public class TicketService {

    private final TicketRepository ticketRepository;
    private final DistribuidorRepository distribuidorRepository;
    private final ClienteDirectoRepository clienteDirectoRepository;
    private final EmpleadoRepository empleadoRepository;

    public TicketService(final TicketRepository ticketRepository,
            final DistribuidorRepository distribuidorRepository,
            final ClienteDirectoRepository clienteDirectoRepository,
            final EmpleadoRepository empleadoRepository) {
        this.ticketRepository = ticketRepository;
        this.distribuidorRepository = distribuidorRepository;
        this.clienteDirectoRepository = clienteDirectoRepository;
        this.empleadoRepository = empleadoRepository;
    }

    public List<TicketDTO> findAll() {
        final List<Ticket> tickets = ticketRepository.findAll(Sort.by("idTicket"));
        return tickets.stream()
                .map(ticket -> mapToDTO(ticket, new TicketDTO()))
                .toList();
    }

    public TicketDTO get(final String idTicket) {
        return ticketRepository.findById(idTicket)
                .map(ticket -> mapToDTO(ticket, new TicketDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final TicketDTO ticketDTO) {
        final Ticket ticket = new Ticket();
        mapToEntity(ticketDTO, ticket);
        ticket.setIdTicket(ticketDTO.getIdTicket());
        return ticketRepository.save(ticket).getIdTicket();
    }

    public void update(final String idTicket, final TicketDTO ticketDTO) {
        final Ticket ticket = ticketRepository.findById(idTicket)
                .orElseThrow(NotFoundException::new);
        mapToEntity(ticketDTO, ticket);
        ticketRepository.save(ticket);
    }

    public void delete(final String idTicket) {
        ticketRepository.deleteById(idTicket);
    }

    private TicketDTO mapToDTO(final Ticket ticket, final TicketDTO ticketDTO) {
        ticketDTO.setIdTicket(ticket.getIdTicket());
        ticketDTO.setAsunto(ticket.getAsunto());
        ticketDTO.setDescripcion(ticket.getDescripcion());
        ticketDTO.setPrioridad(ticket.getPrioridad());
        ticketDTO.setEstado(ticket.getEstado());
        ticketDTO.setDistribuidor(ticket.getDistribuidor() == null ? null : ticket.getDistribuidor().getIdDistribuidor());
        ticketDTO.setCliente(ticket.getCliente() == null ? null : ticket.getCliente().getIdCliente());
        ticketDTO.setEmpleado(ticket.getEmpleado() == null ? null : ticket.getEmpleado().getIdEmpleado());
        ticketDTO.setHistorialTicketUsuarioEmpleadoes(ticket.getHistorialTicketUsuarioEmpleadoes().stream()
                .map(empleado -> empleado.getIdEmpleado())
                .toList());
        return ticketDTO;
    }

    private Ticket mapToEntity(final TicketDTO ticketDTO, final Ticket ticket) {
        ticket.setAsunto(ticketDTO.getAsunto());
        ticket.setDescripcion(ticketDTO.getDescripcion());
        ticket.setPrioridad(ticketDTO.getPrioridad());
        ticket.setEstado(ticketDTO.getEstado());
        final Distribuidor distribuidor = ticketDTO.getDistribuidor() == null ? null : distribuidorRepository.findById(ticketDTO.getDistribuidor())
                .orElseThrow(() -> new NotFoundException("distribuidor not found"));
        ticket.setDistribuidor(distribuidor);
        final ClienteDirecto cliente = ticketDTO.getCliente() == null ? null : clienteDirectoRepository.findById(ticketDTO.getCliente())
                .orElseThrow(() -> new NotFoundException("cliente not found"));
        ticket.setCliente(cliente);
        final Empleado empleado = ticketDTO.getEmpleado() == null ? null : empleadoRepository.findById(ticketDTO.getEmpleado())
                .orElseThrow(() -> new NotFoundException("empleado not found"));
        ticket.setEmpleado(empleado);
        final List<Empleado> historialTicketUsuarioEmpleadoes = empleadoRepository.findAllById(
                ticketDTO.getHistorialTicketUsuarioEmpleadoes() == null ? List.of() : ticketDTO.getHistorialTicketUsuarioEmpleadoes());
        if (historialTicketUsuarioEmpleadoes.size() != (ticketDTO.getHistorialTicketUsuarioEmpleadoes() == null ? 0 : ticketDTO.getHistorialTicketUsuarioEmpleadoes().size())) {
            throw new NotFoundException("one of historialTicketUsuarioEmpleadoes not found");
        }
        ticket.setHistorialTicketUsuarioEmpleadoes(new HashSet<>(historialTicketUsuarioEmpleadoes));
        return ticket;
    }

    public boolean idTicketExists(final String idTicket) {
        return ticketRepository.existsByIdTicketIgnoreCase(idTicket);
    }

}
