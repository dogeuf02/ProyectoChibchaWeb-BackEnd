package com.debloopers.chibchaweb.rest;

import com.debloopers.chibchaweb.model.TicketDTO;
import com.debloopers.chibchaweb.service.TicketService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/tickets", produces = MediaType.APPLICATION_JSON_VALUE)
public class TicketResource {

    private final TicketService ticketService;

    public TicketResource(final TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public ResponseEntity<List<TicketDTO>> getAllTickets() {
        return ResponseEntity.ok(ticketService.findAll());
    }

    @GetMapping("/{idTicket}")
    public ResponseEntity<TicketDTO> getTicket(
            @PathVariable(name = "idTicket") final Integer idTicket) {
        return ResponseEntity.ok(ticketService.get(idTicket));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createTicket(@RequestBody @Valid final TicketDTO ticketDTO) {
        final Integer createdIdTicket = ticketService.create(ticketDTO);
        return new ResponseEntity<>(createdIdTicket, HttpStatus.CREATED);
    }

    @PutMapping("/{idTicket}")
    public ResponseEntity<Integer> updateTicket(
            @PathVariable(name = "idTicket") final Integer idTicket,
            @RequestBody @Valid final TicketDTO ticketDTO) {
        ticketService.update(idTicket, ticketDTO);
        return ResponseEntity.ok(idTicket);
    }

    @DeleteMapping("/{idTicket}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteTicket(
            @PathVariable(name = "idTicket") final Integer idTicket) {
        ticketService.delete(idTicket);
        return ResponseEntity.noContent().build();
    }

}
