package com.debloopers.chibchaweb.controller;

import com.debloopers.chibchaweb.dto.MedioPagoDTO;
import com.debloopers.chibchaweb.service.MedioPagoService;
import com.debloopers.chibchaweb.util.ReferencedException;
import com.debloopers.chibchaweb.util.ReferencedWarning;
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
@RequestMapping(value = "/api/medioPago", produces = MediaType.APPLICATION_JSON_VALUE)
public class MedioPagoController {

    private final MedioPagoService medioPagoService;

    public MedioPagoController(final MedioPagoService medioPagoService) {
        this.medioPagoService = medioPagoService;
    }

    @PreAuthorize("hasAuthority('Administrador')")
    @GetMapping
    public ResponseEntity<List<MedioPagoDTO>> getAllMedioPagos() {
        return ResponseEntity.ok(medioPagoService.findAll());
    }

    @PreAuthorize("hasAnyAuthority('Administrador','Distribuidor','Cliente')")
    @GetMapping("/{idMedioPago}")
    public ResponseEntity<MedioPagoDTO> getMedioPago(
            @PathVariable(name = "idMedioPago") final Integer idMedioPago) {
        return ResponseEntity.ok(medioPagoService.get(idMedioPago));
    }

    @PreAuthorize("hasAnyAuthority('Administrador','Cliente')")
    @Operation(summary = "Obtener medios de pago de un cliente")
    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<MedioPagoDTO>> getMediosPorCliente(@PathVariable Integer idCliente) {
        return ResponseEntity.ok(medioPagoService.findAllByCliente(idCliente));
    }

    @PreAuthorize("hasAnyAuthority('Administrador','Distribuidor')")
    @Operation(summary = "Obtener medios de pago de un distribuidor")
    @GetMapping("/distribuidor/{idDistribuidor}")
    public ResponseEntity<List<MedioPagoDTO>> getMediosPorDistribuidor(@PathVariable Integer idDistribuidor) {
        return ResponseEntity.ok(medioPagoService.findAllByDistribuidor(idDistribuidor));
    }

    @PreAuthorize("hasAnyAuthority('Administrador','Distribuidor','Cliente')")
    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createMedioPago(
            @RequestBody @Valid final MedioPagoDTO medioPagoDTO) {
        final Integer createdIdMedioPago = medioPagoService.create(medioPagoDTO);
        return new ResponseEntity<>(createdIdMedioPago, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('Administrador','Distribuidor','Cliente')")
    @PutMapping("/{idMedioPago}")
    public ResponseEntity<Integer> updateMedioPago(
            @PathVariable(name = "idMedioPago") final Integer idMedioPago,
            @RequestBody @Valid final MedioPagoDTO medioPagoDTO) {
        medioPagoService.update(idMedioPago, medioPagoDTO);
        return ResponseEntity.ok(idMedioPago);
    }

    @PreAuthorize("hasAnyAuthority('Administrador','Distribuidor','Cliente')")
    @DeleteMapping("/{idMedioPago}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteMedioPago(
            @PathVariable(name = "idMedioPago") final Integer idMedioPago) {
        final ReferencedWarning referencedWarning = medioPagoService.getReferencedWarning(idMedioPago);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        medioPagoService.delete(idMedioPago);
        return ResponseEntity.noContent().build();
    }
}