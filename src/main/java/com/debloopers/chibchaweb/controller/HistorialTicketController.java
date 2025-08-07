package com.debloopers.chibchaweb.controller;

import com.debloopers.chibchaweb.dto.HistorialTicketDTO;
import com.debloopers.chibchaweb.service.HistorialTicketService;
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
@RequestMapping(value = "/api/historialTicket", produces = MediaType.APPLICATION_JSON_VALUE)
public class HistorialTicketController {

    private final HistorialTicketService historialTicketService;

    public HistorialTicketController(final HistorialTicketService historialTicketService) {
        this.historialTicketService = historialTicketService;
    }

    @PreAuthorize("hasAnyAuthority('Administrador','Empleado')")
    @GetMapping
    public ResponseEntity<List<HistorialTicketDTO>> getAllHistorialTickets() {
        return ResponseEntity.ok(historialTicketService.findAll());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{idHistorialTicket}")
    public ResponseEntity<HistorialTicketDTO> getHistorialTicket(
            @PathVariable(name = "idHistorialTicket") final Integer idHistorialTicket) {
        return ResponseEntity.ok(historialTicketService.get(idHistorialTicket));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createHistorialTicket(
            @RequestBody @Valid final HistorialTicketDTO historialTicketDTO) {
        final Integer createdIdHistorialTicket = historialTicketService.create(historialTicketDTO);
        return new ResponseEntity<>(createdIdHistorialTicket, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('Administrador','Empleado')")
    @PutMapping("/{idHistorialTicket}")
    public ResponseEntity<Integer> updateHistorialTicket(
            @PathVariable(name = "idHistorialTicket") final Integer idHistorialTicket,
            @RequestBody @Valid final HistorialTicketDTO historialTicketDTO) {
        historialTicketService.update(idHistorialTicket, historialTicketDTO);
        return ResponseEntity.ok(idHistorialTicket);
    }

    @PreAuthorize("hasAuthority('Administrador')")
    @DeleteMapping("/{idHistorialTicket}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteHistorialTicket(
            @PathVariable(name = "idHistorialTicket") final Integer idHistorialTicket) {
        historialTicketService.delete(idHistorialTicket);
        return ResponseEntity.noContent().build();
    }
}