package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.dto.HistorialTicketDTO;
import com.debloopers.chibchaweb.entity.Empleado;
import com.debloopers.chibchaweb.entity.HistorialTicket;
import com.debloopers.chibchaweb.entity.Ticket;
import com.debloopers.chibchaweb.repository.EmpleadoRepository;
import com.debloopers.chibchaweb.repository.HistorialTicketRepository;
import com.debloopers.chibchaweb.repository.TicketRepository;
import com.debloopers.chibchaweb.util.NotFoundException;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class HistorialTicketService {

    private final HistorialTicketRepository historialTicketRepository;
    private final TicketRepository ticketRepository;
    private final EmpleadoRepository empleadoRepository;

    public HistorialTicketService(final HistorialTicketRepository historialTicketRepository,
                                  final TicketRepository ticketRepository, final EmpleadoRepository empleadoRepository) {
        this.historialTicketRepository = historialTicketRepository;
        this.ticketRepository = ticketRepository;
        this.empleadoRepository = empleadoRepository;
    }

    public List<HistorialTicketDTO> findAll() {
        final List<HistorialTicket> historialTickets = historialTicketRepository.findAll(Sort.by("idHistorialTicket"));
        return historialTickets.stream()
                .map(historialTicket -> mapToDTO(historialTicket, new HistorialTicketDTO()))
                .toList();
    }

    public HistorialTicketDTO get(final Integer idHistorialTicket) {
        return historialTicketRepository.findById(idHistorialTicket)
                .map(historialTicket -> mapToDTO(historialTicket, new HistorialTicketDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public List<HistorialTicket> obtenerHistorialPorIdTicket(String idTicket) {
        return historialTicketRepository.findByTicket_IdTicket(idTicket);
    }

    public Integer create(final HistorialTicketDTO historialTicketDTO) {
        final HistorialTicket historialTicket = new HistorialTicket();
        mapToEntity(historialTicketDTO, historialTicket);
        return historialTicketRepository.save(historialTicket).getIdHistorialTicket();
    }

    public void update(final Integer idHistorialTicket,
            final HistorialTicketDTO historialTicketDTO) {
        final HistorialTicket historialTicket = historialTicketRepository.findById(idHistorialTicket)
                .orElseThrow(NotFoundException::new);
        mapToEntity(historialTicketDTO, historialTicket);
        historialTicketRepository.save(historialTicket);
    }

    public void delete(final Integer idHistorialTicket) {
        historialTicketRepository.deleteById(idHistorialTicket);
    }

    private HistorialTicketDTO mapToDTO(final HistorialTicket historialTicket,
                                        final HistorialTicketDTO historialTicketDTO) {
        historialTicketDTO.setIdHistorialTicket(historialTicket.getIdHistorialTicket());
        historialTicketDTO.setAccionTicket(historialTicket.getAccionTicket());
        historialTicketDTO.setComentarios(historialTicket.getComentarios());
        historialTicketDTO.setFechaAccion(historialTicket.getFechaAccion());
        historialTicketDTO.setTicket(historialTicket.getTicket() == null ? null : historialTicket.getTicket().getIdTicket());
        historialTicketDTO.setEmpleadoRealizador(historialTicket.getEmpleadoRealizador() == null ? null : historialTicket.getEmpleadoRealizador().getIdEmpleado());
        historialTicketDTO.setEmpleadoReceptor(historialTicket.getEmpleadoReceptor() == null ? null : historialTicket.getEmpleadoReceptor().getIdEmpleado());
        return historialTicketDTO;
    }

    private HistorialTicket mapToEntity(final HistorialTicketDTO historialTicketDTO,
            final HistorialTicket historialTicket) {
        historialTicket.setAccionTicket(historialTicketDTO.getAccionTicket());
        historialTicket.setComentarios(historialTicketDTO.getComentarios());
        historialTicket.setFechaAccion(historialTicketDTO.getFechaAccion());
        final Ticket ticket = historialTicketDTO.getTicket() == null ? null : ticketRepository.findById(historialTicketDTO.getTicket())
                .orElseThrow(() -> new NotFoundException("ticket not found"));
        historialTicket.setTicket(ticket);
        final Empleado empleadoRealizador = historialTicketDTO.getEmpleadoRealizador() == null ? null : empleadoRepository.findById(historialTicketDTO.getEmpleadoRealizador())
                .orElseThrow(() -> new NotFoundException("empleadoRealizador not found"));
        historialTicket.setEmpleadoRealizador(empleadoRealizador);
        final Empleado empleadoReceptor = historialTicketDTO.getEmpleadoReceptor() == null ? null : empleadoRepository.findById(historialTicketDTO.getEmpleadoReceptor())
                .orElseThrow(() -> new NotFoundException("empleadoReceptor not found"));
        historialTicket.setEmpleadoReceptor(empleadoReceptor);
        return historialTicket;
    }
}