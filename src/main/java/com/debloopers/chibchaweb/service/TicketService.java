package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.entity.*;
import com.debloopers.chibchaweb.dto.TicketDTO;
import com.debloopers.chibchaweb.repository.*;
import com.debloopers.chibchaweb.util.NotFoundException;

import java.util.List;

import com.debloopers.chibchaweb.util.ReferencedWarning;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final DistribuidorRepository distribuidorRepository;
    private final ClienteDirectoRepository clienteDirectoRepository;
    private final HistorialTicketRepository historialTicketRepository;

    public TicketService(final TicketRepository ticketRepository,
                         final DistribuidorRepository distribuidorRepository,
                         final ClienteDirectoRepository clienteDirectoRepository,
                         final HistorialTicketRepository historialTicketRepository) {
        this.ticketRepository = ticketRepository;
        this.distribuidorRepository = distribuidorRepository;
        this.clienteDirectoRepository = clienteDirectoRepository;
        this.historialTicketRepository = historialTicketRepository;
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
        ticketDTO.setNivelComplejidad(ticket.getNivelComplejidad());
        ticketDTO.setEstado(ticket.getEstado());
        ticketDTO.setFechaCreacion(ticket.getFechaCreacion());
        ticketDTO.setFechaCierre(ticket.getFechaCierre());
        ticketDTO.setDistribuidor(ticket.getDistribuidor() == null ? null : ticket.getDistribuidor().getIdDistribuidor());
        ticketDTO.setCliente(ticket.getCliente() == null ? null : ticket.getCliente().getIdCliente());
        return ticketDTO;
    }

    private Ticket mapToEntity(final TicketDTO ticketDTO, final Ticket ticket) {
        ticket.setAsunto(ticketDTO.getAsunto());
        ticket.setDescripcion(ticketDTO.getDescripcion());
        ticket.setNivelComplejidad(ticketDTO.getNivelComplejidad());
        ticket.setEstado(ticketDTO.getEstado());
        ticket.setFechaCreacion(ticketDTO.getFechaCreacion());
        ticket.setFechaCierre(ticketDTO.getFechaCierre());
        final Distribuidor distribuidor = ticketDTO.getDistribuidor() == null ? null : distribuidorRepository.findById(ticketDTO.getDistribuidor())
                .orElseThrow(() -> new NotFoundException("distribuidor not found"));
        ticket.setDistribuidor(distribuidor);
        final ClienteDirecto cliente = ticketDTO.getCliente() == null ? null : clienteDirectoRepository.findById(ticketDTO.getCliente())
                .orElseThrow(() -> new NotFoundException("cliente not found"));
        ticket.setCliente(cliente);
        return ticket;
    }

    public ReferencedWarning getReferencedWarning(final String idTicket) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Ticket ticket = ticketRepository.findById(idTicket)
                .orElseThrow(NotFoundException::new);
        final HistorialTicket ticketHistorialTicket = historialTicketRepository.findFirstByTicket(ticket);
        if (ticketHistorialTicket != null) {
            referencedWarning.setKey("ticket.historialTicket.ticket.referenced");
            referencedWarning.addParam(ticketHistorialTicket.getIdHistorialTicket());
            return referencedWarning;
        }
        return null;
    }
}