package com.debloopers.chibchaweb.rest;

import com.debloopers.chibchaweb.model.SolicitudDomDistribuidorDTO;
import com.debloopers.chibchaweb.service.SolicitudDomDistribuidorService;
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
@RequestMapping(value = "/api/solicitudDomDistribuidors", produces = MediaType.APPLICATION_JSON_VALUE)
public class SolicitudDomDistribuidorResource {

    private final SolicitudDomDistribuidorService solicitudDomDistribuidorService;

    public SolicitudDomDistribuidorResource(
            final SolicitudDomDistribuidorService solicitudDomDistribuidorService) {
        this.solicitudDomDistribuidorService = solicitudDomDistribuidorService;
    }

    @GetMapping
    public ResponseEntity<List<SolicitudDomDistribuidorDTO>> getAllSolicitudDomDistribuidors() {
        return ResponseEntity.ok(solicitudDomDistribuidorService.findAll());
    }

    @GetMapping("/{tld}")
    public ResponseEntity<SolicitudDomDistribuidorDTO> getSolicitudDomDistribuidor(
            @PathVariable(name = "tld") final String tld) {
        return ResponseEntity.ok(solicitudDomDistribuidorService.get(tld));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<String> createSolicitudDomDistribuidor(
            @RequestBody @Valid final SolicitudDomDistribuidorDTO solicitudDomDistribuidorDTO) {
        final String createdTld = solicitudDomDistribuidorService.create(solicitudDomDistribuidorDTO);
        return new ResponseEntity<>('"' + createdTld + '"', HttpStatus.CREATED);
    }

    @PutMapping("/{tld}")
    public ResponseEntity<String> updateSolicitudDomDistribuidor(
            @PathVariable(name = "tld") final String tld,
            @RequestBody @Valid final SolicitudDomDistribuidorDTO solicitudDomDistribuidorDTO) {
        solicitudDomDistribuidorService.update(tld, solicitudDomDistribuidorDTO);
        return ResponseEntity.ok('"' + tld + '"');
    }

    @DeleteMapping("/{tld}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSolicitudDomDistribuidor(
            @PathVariable(name = "tld") final String tld) {
        solicitudDomDistribuidorService.delete(tld);
        return ResponseEntity.noContent().build();
    }

}
