package com.debloopers.chibchaweb.rest;

import com.debloopers.chibchaweb.model.DistribuidorDTO;
import com.debloopers.chibchaweb.service.DistribuidorService;
import com.debloopers.chibchaweb.util.ReferencedException;
import com.debloopers.chibchaweb.util.ReferencedWarning;
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
@RequestMapping(value = "/api/distribuidors", produces = MediaType.APPLICATION_JSON_VALUE)
public class DistribuidorResource {

    private final DistribuidorService distribuidorService;

    public DistribuidorResource(final DistribuidorService distribuidorService) {
        this.distribuidorService = distribuidorService;
    }

    @GetMapping
    public ResponseEntity<List<DistribuidorDTO>> getAllDistribuidors() {
        return ResponseEntity.ok(distribuidorService.findAll());
    }

    @GetMapping("/{numeroDocEmpresa}")
    public ResponseEntity<DistribuidorDTO> getDistribuidor(
            @PathVariable(name = "numeroDocEmpresa") final String numeroDocEmpresa) {
        return ResponseEntity.ok(distribuidorService.get(numeroDocEmpresa));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<String> createDistribuidor(
            @RequestBody @Valid final DistribuidorDTO distribuidorDTO) {
        final String createdNumeroDocEmpresa = distribuidorService.create(distribuidorDTO);
        return new ResponseEntity<>('"' + createdNumeroDocEmpresa + '"', HttpStatus.CREATED);
    }

    @PutMapping("/{numeroDocEmpresa}")
    public ResponseEntity<String> updateDistribuidor(
            @PathVariable(name = "numeroDocEmpresa") final String numeroDocEmpresa,
            @RequestBody @Valid final DistribuidorDTO distribuidorDTO) {
        distribuidorService.update(numeroDocEmpresa, distribuidorDTO);
        return ResponseEntity.ok('"' + numeroDocEmpresa + '"');
    }

    @DeleteMapping("/{numeroDocEmpresa}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDistribuidor(
            @PathVariable(name = "numeroDocEmpresa") final String numeroDocEmpresa) {
        final ReferencedWarning referencedWarning = distribuidorService.getReferencedWarning(numeroDocEmpresa);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        distribuidorService.delete(numeroDocEmpresa);
        return ResponseEntity.noContent().build();
    }

}
