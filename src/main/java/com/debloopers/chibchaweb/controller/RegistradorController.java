package com.debloopers.chibchaweb.controller;

import com.debloopers.chibchaweb.dto.RegistradorDTO;
import com.debloopers.chibchaweb.service.RegistradorService;
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
@RequestMapping(value = "/api/registrador", produces = MediaType.APPLICATION_JSON_VALUE)
public class RegistradorController {

    private final RegistradorService registradorService;

    public RegistradorController(final RegistradorService registradorService) {
        this.registradorService = registradorService;
    }

    @PreAuthorize("hasAnyAuthority('Administrador','Empleado','Distribuidor')")
    @GetMapping
    public ResponseEntity<List<RegistradorDTO>> getAllRegistradors() {
        return ResponseEntity.ok(registradorService.findAll());
    }

    @PreAuthorize("hasAnyAuthority('Administrador','Empleado','Distribuidor')")
    @GetMapping("/{idRegistrador}")
    public ResponseEntity<RegistradorDTO> getRegistrador(
            @PathVariable(name = "idRegistrador") final Integer idRegistrador) {
        return ResponseEntity.ok(registradorService.get(idRegistrador));
    }

    @PreAuthorize("permitAll()")
    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createRegistrador(
            @RequestBody @Valid final RegistradorDTO registradorDTO) {
        final Integer createdIdRegistrador = registradorService.create(registradorDTO);
        return new ResponseEntity<>(createdIdRegistrador, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('Administrador','Distribuidor')")
    @PutMapping("/{idRegistrador}")
    public ResponseEntity<Integer> updateRegistrador(
            @PathVariable(name = "idRegistrador") final Integer idRegistrador,
            @RequestBody @Valid final RegistradorDTO registradorDTO) {
        registradorService.update(idRegistrador, registradorDTO);
        return ResponseEntity.ok(idRegistrador);
    }

    @PreAuthorize("hasAuthority('Administrador')")
    @DeleteMapping("/{idRegistrador}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteRegistrador(
            @PathVariable(name = "idRegistrador") final Integer idRegistrador) {
        registradorService.delete(idRegistrador);
        return ResponseEntity.noContent().build();
    }
}