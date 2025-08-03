package com.debloopers.chibchaweb.controller;

import com.debloopers.chibchaweb.dto.DominioDTO;
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
@RequestMapping(value = "/api/dominio", produces = MediaType.APPLICATION_JSON_VALUE)
public class DominioController {

    private final DominioService dominioService;

    public DominioController(final DominioService dominioService) {
        this.dominioService = dominioService;
    }

    @GetMapping
    public ResponseEntity<List<DominioDTO>> getAllDominios() {
        return ResponseEntity.ok(dominioService.findAll());
    }

    @GetMapping("/{idDominio}")
    public ResponseEntity<DominioDTO> getDominio(
            @PathVariable(name = "idDominio") final Integer idDominio) {
        return ResponseEntity.ok(dominioService.get(idDominio));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createDominio(@RequestBody @Valid final DominioDTO dominioDTO) {
        final Integer createdIdDominio = dominioService.create(dominioDTO);
        return new ResponseEntity<>(createdIdDominio, HttpStatus.CREATED);
    }

    @PutMapping("/{idDominio}")
    public ResponseEntity<Integer> updateDominio(
            @PathVariable(name = "idDominio") final Integer idDominio,
            @RequestBody @Valid final DominioDTO dominioDTO) {
        dominioService.update(idDominio, dominioDTO);
        return ResponseEntity.ok(idDominio);
    }

    @DeleteMapping("/{idDominio}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDominio(
            @PathVariable(name = "idDominio") final Integer idDominio) {
        final ReferencedWarning referencedWarning = dominioService.getReferencedWarning(idDominio);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        dominioService.delete(idDominio);
        return ResponseEntity.noContent().build();
    }
}