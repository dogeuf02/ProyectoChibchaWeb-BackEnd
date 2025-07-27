package com.debloopers.chibchaweb.rest;

import com.debloopers.chibchaweb.model.SolicitudDomClienteDTO;
import com.debloopers.chibchaweb.service.SolicitudDomClienteService;
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
@RequestMapping(value = "/api/solicitudDomClientes", produces = MediaType.APPLICATION_JSON_VALUE)
public class SolicitudDomClienteResource {

    private final SolicitudDomClienteService solicitudDomClienteService;

    public SolicitudDomClienteResource(
            final SolicitudDomClienteService solicitudDomClienteService) {
        this.solicitudDomClienteService = solicitudDomClienteService;
    }

    @GetMapping
    public ResponseEntity<List<SolicitudDomClienteDTO>> getAllSolicitudDomClientes() {
        return ResponseEntity.ok(solicitudDomClienteService.findAll());
    }

    @GetMapping("/{tld}")
    public ResponseEntity<SolicitudDomClienteDTO> getSolicitudDomCliente(
            @PathVariable(name = "tld") final String tld) {
        return ResponseEntity.ok(solicitudDomClienteService.get(tld));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<String> createSolicitudDomCliente(
            @RequestBody @Valid final SolicitudDomClienteDTO solicitudDomClienteDTO) {
        final String createdTld = solicitudDomClienteService.create(solicitudDomClienteDTO);
        return new ResponseEntity<>('"' + createdTld + '"', HttpStatus.CREATED);
    }

    @PutMapping("/{tld}")
    public ResponseEntity<String> updateSolicitudDomCliente(
            @PathVariable(name = "tld") final String tld,
            @RequestBody @Valid final SolicitudDomClienteDTO solicitudDomClienteDTO) {
        solicitudDomClienteService.update(tld, solicitudDomClienteDTO);
        return ResponseEntity.ok('"' + tld + '"');
    }

    @DeleteMapping("/{tld}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSolicitudDomCliente(
            @PathVariable(name = "tld") final String tld) {
        solicitudDomClienteService.delete(tld);
        return ResponseEntity.noContent().build();
    }

}
