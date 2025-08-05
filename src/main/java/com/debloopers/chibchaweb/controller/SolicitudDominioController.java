package com.debloopers.chibchaweb.controller;

import com.debloopers.chibchaweb.dto.SolicitudDominioDTO;
import com.debloopers.chibchaweb.entity.SolicitudDominio;
import com.debloopers.chibchaweb.service.SolicitudDominioService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping(value = "/api/solicitudDominio", produces = MediaType.APPLICATION_JSON_VALUE)
public class SolicitudDominioController {

    private final SolicitudDominioService solicitudDominioService;

    public SolicitudDominioController(final SolicitudDominioService solicitudDominioService) {
        this.solicitudDominioService = solicitudDominioService;
    }

    @GetMapping
    public ResponseEntity<List<SolicitudDominioDTO>> getAllSolicitudDominios() {
        return ResponseEntity.ok(solicitudDominioService.findAll());
    }

    @GetMapping("/{idSolicitud}")
    public ResponseEntity<SolicitudDominioDTO> getSolicitudDominio(
            @PathVariable(name = "idSolicitud") final Integer idSolicitud) {
        return ResponseEntity.ok(solicitudDominioService.get(idSolicitud));
    }

    @Operation(summary = "Obtener todas las solicitudes de dominio realizadas por un cliente")
    @GetMapping("/cliente/{idCliente}")
    public List<SolicitudDominioDTO> getSolicitudesPorCliente(@PathVariable Integer idCliente) {
        return solicitudDominioService.obtenerSolicitudesPorCliente(idCliente);
    }

    @Operation(summary = "Obtener todas las solicitudes de dominio realizadas por un distribuidor")
    @GetMapping("/distribuidor/{idDistribuidor}")
    public List<SolicitudDominioDTO> getSolicitudesPorDistribuidor(@PathVariable Integer idDistribuidor) {
        return solicitudDominioService.obtenerSolicitudesPorDistribuidor(idDistribuidor);
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createSolicitudDominio(
            @RequestBody @Valid final SolicitudDominioDTO solicitudDominioDTO) {
        final Integer createdIdSolicitud = solicitudDominioService.create(solicitudDominioDTO);
        return new ResponseEntity<>(createdIdSolicitud, HttpStatus.CREATED);
    }

    @PutMapping("/{idSolicitud}")
    public ResponseEntity<Integer> updateSolicitudDominio(
            @PathVariable(name = "idSolicitud") final Integer idSolicitud,
            @RequestBody @Valid final SolicitudDominioDTO solicitudDominioDTO) {
        solicitudDominioService.update(idSolicitud, solicitudDominioDTO);
        return ResponseEntity.ok(idSolicitud);
    }

    @DeleteMapping("/{idSolicitud}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSolicitudDominio(
            @PathVariable(name = "idSolicitud") final Integer idSolicitud) {
        solicitudDominioService.delete(idSolicitud);
        return ResponseEntity.noContent().build();
    }
}