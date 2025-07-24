package com.debloopers.chibchaweb.rest;

import com.debloopers.chibchaweb.model.SolicitudDomCdDTO;
import com.debloopers.chibchaweb.service.SolicitudDomCdService;
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
@RequestMapping(value = "/api/solicitudDomCds", produces = MediaType.APPLICATION_JSON_VALUE)
public class SolicitudDomCdResource {

    private final SolicitudDomCdService solicitudDomCdService;

    public SolicitudDomCdResource(final SolicitudDomCdService solicitudDomCdService) {
        this.solicitudDomCdService = solicitudDomCdService;
    }

    @GetMapping
    public ResponseEntity<List<SolicitudDomCdDTO>> getAllSolicitudDomCds() {
        return ResponseEntity.ok(solicitudDomCdService.findAll());
    }

    @GetMapping("/{tld}")
    public ResponseEntity<SolicitudDomCdDTO> getSolicitudDomCd(
            @PathVariable(name = "tld") final String tld) {
        return ResponseEntity.ok(solicitudDomCdService.get(tld));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<String> createSolicitudDomCd(
            @RequestBody @Valid final SolicitudDomCdDTO solicitudDomCdDTO) {
        final String createdTld = solicitudDomCdService.create(solicitudDomCdDTO);
        return new ResponseEntity<>('"' + createdTld + '"', HttpStatus.CREATED);
    }

    @PutMapping("/{tld}")
    public ResponseEntity<String> updateSolicitudDomCd(@PathVariable(name = "tld") final String tld,
            @RequestBody @Valid final SolicitudDomCdDTO solicitudDomCdDTO) {
        solicitudDomCdService.update(tld, solicitudDomCdDTO);
        return ResponseEntity.ok('"' + tld + '"');
    }

    @DeleteMapping("/{tld}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSolicitudDomCd(@PathVariable(name = "tld") final String tld) {
        solicitudDomCdService.delete(tld);
        return ResponseEntity.noContent().build();
    }

}
