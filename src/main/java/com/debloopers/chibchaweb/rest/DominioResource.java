package com.debloopers.chibchaweb.rest;

import com.debloopers.chibchaweb.model.DominioDTO;
import com.debloopers.chibchaweb.service.DominioService;
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
@RequestMapping(value = "/api/dominios", produces = MediaType.APPLICATION_JSON_VALUE)
public class DominioResource {

    private final DominioService dominioService;

    public DominioResource(final DominioService dominioService) {
        this.dominioService = dominioService;
    }

    @GetMapping
    public ResponseEntity<List<DominioDTO>> getAllDominios() {
        return ResponseEntity.ok(dominioService.findAll());
    }

    @GetMapping("/{nombreDominio}")
    public ResponseEntity<DominioDTO> getDominio(
            @PathVariable(name = "nombreDominio") final String nombreDominio) {
        return ResponseEntity.ok(dominioService.get(nombreDominio));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<String> createDominio(@RequestBody @Valid final DominioDTO dominioDTO) {
        final String createdNombreDominio = dominioService.create(dominioDTO);
        return new ResponseEntity<>('"' + createdNombreDominio + '"', HttpStatus.CREATED);
    }

    @PutMapping("/{nombreDominio}")
    public ResponseEntity<String> updateDominio(
            @PathVariable(name = "nombreDominio") final String nombreDominio,
            @RequestBody @Valid final DominioDTO dominioDTO) {
        dominioService.update(nombreDominio, dominioDTO);
        return ResponseEntity.ok('"' + nombreDominio + '"');
    }

    @DeleteMapping("/{nombreDominio}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDominio(
            @PathVariable(name = "nombreDominio") final String nombreDominio) {
        final ReferencedWarning referencedWarning = dominioService.getReferencedWarning(nombreDominio);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        dominioService.delete(nombreDominio);
        return ResponseEntity.noContent().build();
    }

}
