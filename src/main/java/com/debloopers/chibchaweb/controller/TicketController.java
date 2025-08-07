package com.debloopers.chibchaweb.controller;

import com.debloopers.chibchaweb.dto.TicketConHistorialDTO;
import com.debloopers.chibchaweb.dto.TicketDTO;
import com.debloopers.chibchaweb.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/ticket", produces = MediaType.APPLICATION_JSON_VALUE)
public class TicketController {

    private final TicketService ticketService;

    public TicketController(final TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PreAuthorize("hasAnyAuthority('Administrador','Empleado','Cliente')")
    @GetMapping
    public ResponseEntity<List<TicketDTO>> getAllTickets() {
        return ResponseEntity.ok(ticketService.findAll());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{idTicket}")
    public ResponseEntity<TicketDTO> getTicket(
            @PathVariable(name = "idTicket") final String idTicket) {
        return ResponseEntity.ok(ticketService.get(idTicket));
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Obtener todo el historial de un ticket mediante su ID")
    @GetMapping("/obtenerHistorial/{idTicket}")
    public ResponseEntity<TicketConHistorialDTO> obtenerTicketConHistorial(@PathVariable String idTicket) {
        return ResponseEntity.ok(ticketService.obtenerTicketConHistorial(idTicket));
    }

    @PreAuthorize("hasAnyAuthority('Administrador','Cliente','Empleado')")
    @Operation(summary = "Obtener todos los tickets de un cliente mediante su ID")
    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<TicketDTO>> listarPorCliente(@PathVariable Integer idCliente) {
        return ResponseEntity.ok(ticketService.obtenerTicketsPorCliente(idCliente));
    }

    @PreAuthorize("hasAnyAuthority('Administrador','Distribuidor','Empleado')")
    @Operation(summary = "Obtener todos los tickets de un distribuidor mediante su ID")
    @GetMapping("/distribuidor/{idDistribuidor}")
    public ResponseEntity<List<TicketDTO>> listarPorDistribuidor(@PathVariable Integer idDistribuidor) {
        return ResponseEntity.ok(ticketService.obtenerTicketsPorDistribuidor(idDistribuidor));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<String> createTicket(@RequestBody @Valid final TicketDTO ticketDTO) {
        final String createdIdTicket = ticketService.create(ticketDTO);
        return new ResponseEntity<>('"' + createdIdTicket + '"', HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('Administrador','Empleado')")
    @PutMapping("/{idTicket}")
    public ResponseEntity<String> updateTicket(
            @PathVariable(name = "idTicket") final String idTicket,
            @RequestBody @Valid final TicketDTO ticketDTO) {
        ticketService.update(idTicket, ticketDTO);
        return ResponseEntity.ok('"' + idTicket + '"');
    }

    @PreAuthorize("hasAuthority('Administrador')")
    @DeleteMapping("/{idTicket}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteTicket(
            @PathVariable(name = "idTicket") final String idTicket) {
        ticketService.delete(idTicket);
        return ResponseEntity.noContent().build();
    }
}