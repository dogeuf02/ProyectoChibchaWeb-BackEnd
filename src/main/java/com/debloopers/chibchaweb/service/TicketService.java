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
    private final ClienteDirectoRepository clienteDirectoRepository;
    private final DistribuidorRepository distribuidorRepository;
    private final EmpleadoRepository empleadoRepository;

    public TicketService(final TicketRepository ticketRepository,
            final ClienteDirectoRepository clienteDirectoRepository,
            final DistribuidorRepository distribuidorRepository,
            final EmpleadoRepository empleadoRepository) {
        this.ticketRepository = ticketRepository;
        this.clienteDirectoRepository = clienteDirectoRepository;
        this.distribuidorRepository = distribuidorRepository;
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
        ticketDTO.setNumeroDocEmpresa(ticket.getNumeroDocEmpresa());
        ticketDTO.setAsunto(ticket.getAsunto());
        ticketDTO.setDescripcion(ticket.getDescripcion());
        ticketDTO.setPrioridad(ticket.getPrioridad());
        ticketDTO.setEstado(ticket.getEstado());
        ticketDTO.setCliente(ticket.getCliente() == null ? null : ticket.getCliente().getIdCliente());
        ticketDTO.setNombreTipoDoc(ticket.getNombreTipoDoc() == null ? null : ticket.getNombreTipoDoc().getNumeroDocEmpresa());
        ticketDTO.setEmpleado(ticket.getEmpleado() == null ? null : ticket.getEmpleado().getIdEmpleado());
        ticketDTO.setSolucionEmpleadoes(ticket.getSolucionEmpleadoes().stream()
                .map(empleado -> empleado.getIdEmpleado())
                .toList());
        return ticketDTO;
    }

    private Ticket mapToEntity(final TicketDTO ticketDTO, final Ticket ticket) {
        ticket.setNumeroDocEmpresa(ticketDTO.getNumeroDocEmpresa());
        ticket.setAsunto(ticketDTO.getAsunto());
        ticket.setDescripcion(ticketDTO.getDescripcion());
        ticket.setPrioridad(ticketDTO.getPrioridad());
        ticket.setEstado(ticketDTO.getEstado());
        final ClienteDirecto cliente = ticketDTO.getCliente() == null ? null : clienteDirectoRepository.findById(ticketDTO.getCliente())
                .orElseThrow(() -> new NotFoundException("cliente not found"));
        ticket.setCliente(cliente);
        final Distribuidor nombreTipoDoc = ticketDTO.getNombreTipoDoc() == null ? null : distribuidorRepository.findById(ticketDTO.getNombreTipoDoc())
                .orElseThrow(() -> new NotFoundException("nombreTipoDoc not found"));
        ticket.setNombreTipoDoc(nombreTipoDoc);
        final Empleado empleado = ticketDTO.getEmpleado() == null ? null : empleadoRepository.findById(ticketDTO.getEmpleado())
                .orElseThrow(() -> new NotFoundException("empleado not found"));
        ticket.setEmpleado(empleado);
        final List<Empleado> solucionEmpleadoes = empleadoRepository.findAllById(
                ticketDTO.getSolucionEmpleadoes() == null ? List.of() : ticketDTO.getSolucionEmpleadoes());
        if (solucionEmpleadoes.size() != (ticketDTO.getSolucionEmpleadoes() == null ? 0 : ticketDTO.getSolucionEmpleadoes().size())) {
            throw new NotFoundException("one of solucionEmpleadoes not found");
        }
        ticket.setSolucionEmpleadoes(new HashSet<>(solucionEmpleadoes));
        return ticket;
    }

    public boolean idTicketExists(final String idTicket) {
        return ticketRepository.existsByIdTicketIgnoreCase(idTicket);
    }

}
