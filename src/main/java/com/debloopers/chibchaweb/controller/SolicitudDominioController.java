package com.debloopers.chibchaweb.controller;

import com.debloopers.chibchaweb.dto.ResponseDTO;
import com.debloopers.chibchaweb.dto.SolicitudDominioDTO;
import com.debloopers.chibchaweb.service.SolicitudDominioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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

    @Operation(summary = "Generar archivo XML correspondiente a una solicitud de dominio")
    @GetMapping("/generar-xml/{id}")
    public ResponseEntity<ByteArrayResource> descargarXML(@PathVariable Integer id) throws IOException {
        File archivoXML = solicitudDominioService.generarXMLSolicitudDominio(id);

        byte[] contenido = Files.readAllBytes(archivoXML.toPath());
        ByteArrayResource recurso = new ByteArrayResource(contenido);

        archivoXML.delete();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + archivoXML.getName())
                .contentLength(contenido.length)
                .contentType(MediaType.APPLICATION_XML)
                .body(recurso);
    }

    @PostMapping
    @ApiResponse(responseCode = "200")
    public ResponseEntity<ResponseDTO> createSolicitudDominio(
            @RequestBody @Valid final SolicitudDominioDTO solicitudDominioDTO) {

        ResponseDTO response = solicitudDominioService.create(solicitudDominioDTO);

        if (response.isExito()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/{idSolicitud}")
    public ResponseEntity<Integer> updateSolicitudDominio(
            @PathVariable(name = "idSolicitud") final Integer idSolicitud,
            @RequestBody @Valid final SolicitudDominioDTO solicitudDominioDTO) {
        solicitudDominioService.update(idSolicitud, solicitudDominioDTO);
        return ResponseEntity.ok(idSolicitud);
    }

    @Operation(summary = "Aprobar o rechazar una solicitud de dominio")
    @PutMapping("/gestionarSolicitud/{idSolicitud}")
    public ResponseEntity<ResponseDTO> cambiarEstadoSolicitud(
            @PathVariable Integer idSolicitud,
            @RequestParam boolean aprobar,
            @RequestParam Integer idAdministrador) {
        try {
            ResponseDTO resultado = solicitudDominioService
                    .cambiarEstadoSolicitudDominio(idSolicitud, aprobar, idAdministrador);
            return ResponseEntity.ok(resultado);

        } catch (EntityNotFoundException ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(false, ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(false, "Error processing request."));
        }
    }

    @DeleteMapping("/{idSolicitud}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSolicitudDominio(
            @PathVariable(name = "idSolicitud") final Integer idSolicitud) {
        solicitudDominioService.delete(idSolicitud);
        return ResponseEntity.noContent().build();
    }
}