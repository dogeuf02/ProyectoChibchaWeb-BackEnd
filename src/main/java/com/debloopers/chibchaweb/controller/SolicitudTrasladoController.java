package com.debloopers.chibchaweb.controller;

import com.debloopers.chibchaweb.dto.SolicitudTrasladoDTO;
import com.debloopers.chibchaweb.service.SolicitudTrasladoService;
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
@RequestMapping(value = "/api/solicitudTraslado", produces = MediaType.APPLICATION_JSON_VALUE)
public class SolicitudTrasladoController {

    private final SolicitudTrasladoService solicitudTrasladoService;

    public SolicitudTrasladoController(final SolicitudTrasladoService solicitudTrasladoService) {
        this.solicitudTrasladoService = solicitudTrasladoService;
    }

    @GetMapping
    public ResponseEntity<List<SolicitudTrasladoDTO>> getAllSolicitudTraslados() {
        return ResponseEntity.ok(solicitudTrasladoService.findAll());
    }

    @GetMapping("/{idSolicitudTraslado}")
    public ResponseEntity<SolicitudTrasladoDTO> getSolicitudTraslado(
            @PathVariable(name = "idSolicitudTraslado") final Integer idSolicitudTraslado) {
        return ResponseEntity.ok(solicitudTrasladoService.get(idSolicitudTraslado));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createSolicitudTraslado(
            @RequestBody @Valid final SolicitudTrasladoDTO solicitudTrasladoDTO) {
        final Integer createdIdSolicitudTraslado = solicitudTrasladoService.create(solicitudTrasladoDTO);
        return new ResponseEntity<>(createdIdSolicitudTraslado, HttpStatus.CREATED);
    }

    @PutMapping("/{idSolicitudTraslado}")
    public ResponseEntity<Integer> updateSolicitudTraslado(
            @PathVariable(name = "idSolicitudTraslado") final Integer idSolicitudTraslado,
            @RequestBody @Valid final SolicitudTrasladoDTO solicitudTrasladoDTO) {
        solicitudTrasladoService.update(idSolicitudTraslado, solicitudTrasladoDTO);
        return ResponseEntity.ok(idSolicitudTraslado);
    }

    @DeleteMapping("/{idSolicitudTraslado}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSolicitudTraslado(
            @PathVariable(name = "idSolicitudTraslado") final Integer idSolicitudTraslado) {
        solicitudTrasladoService.delete(idSolicitudTraslado);
        return ResponseEntity.noContent().build();
    }
}