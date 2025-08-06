package com.debloopers.chibchaweb.controller;

import com.debloopers.chibchaweb.dto.*;
import com.debloopers.chibchaweb.service.DistribuidorService;
import com.debloopers.chibchaweb.util.ReferencedException;
import com.debloopers.chibchaweb.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/distribuidor", produces = MediaType.APPLICATION_JSON_VALUE)
public class DistribuidorController {

    private final DistribuidorService distribuidorService;

    public DistribuidorController(final DistribuidorService distribuidorService) {
        this.distribuidorService = distribuidorService;
    }

    @GetMapping
    public ResponseEntity<List<DistribuidorDTO>> getAllDistribuidors() {
        return ResponseEntity.ok(distribuidorService.findAll());
    }

    @GetMapping("/{idDistribuidor}")
    public ResponseEntity<DistribuidorDTO> getDistribuidor(
            @PathVariable(name = "idDistribuidor") final Integer idDistribuidor) {
        return ResponseEntity.ok(distribuidorService.get(idDistribuidor));
    }


    @Operation(summary = "Registrar un distribuidor")
    @PostMapping("/registroDistribuidor")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<ResponseDTO> createDistribuidor(
            @RequestBody @Valid final DistribuidorRegistroRequestDTO distribuidorDTO) {

        ResponseDTO response = distribuidorService.create(distribuidorDTO);

        if (response.isExito()) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Actualizar campos distribuidor")
    @PutMapping("/{idDistribuidor}")
    public ResponseEntity<Integer> updateDistribuidor(
            @PathVariable(name = "idDistribuidor") final Integer idDistribuidor,
            @RequestBody @Valid final DistribuidorDTO distribuidorDTO) {
        distribuidorService.update(idDistribuidor, distribuidorDTO);
        return ResponseEntity.ok(idDistribuidor);
    }

    @Operation(summary = "Obtener los distribuidores con su correo y estado")
    @GetMapping("/obtenerDistribuidores")
    public ResponseEntity<List<DistribuidorConCorreoDTO>> getAllDistribuidoresConCorreo() {
        return ResponseEntity.ok(distribuidorService.findAllWithCorreo());
    }

    @Operation(summary = "Aprobar o rechazar una solicitud de registro de distribuidor")
    @PutMapping("gestionarSolicitudRegistro/{idDistribuidor}")
    public ResponseEntity<String> cambiarEstadoDistribuidor(
            @PathVariable Integer idDistribuidor,
            @RequestParam boolean activar
    ) {
        distribuidorService.cambiarEstadoDistribuidor(idDistribuidor, activar);
        String estado = activar ? "ACTIVO" : "INACTIVO";
        return ResponseEntity.ok("Distributor status updated as of: " + estado);
    }

    @DeleteMapping("/{idDistribuidor}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDistribuidor(
            @PathVariable(name = "idDistribuidor") final Integer idDistribuidor) {
        final ReferencedWarning referencedWarning = distribuidorService.getReferencedWarning(idDistribuidor);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        distribuidorService.delete(idDistribuidor);
        return ResponseEntity.noContent().build();
    }
}