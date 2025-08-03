package com.debloopers.chibchaweb.controller;

import com.debloopers.chibchaweb.dto.CategoriaDistribuidorDTO;
import com.debloopers.chibchaweb.service.CategoriaDistribuidorService;
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
@RequestMapping(value = "/api/categoriaDistribuidor", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoriaDistributionController {

    private final CategoriaDistribuidorService categoriaDistribuidorService;

    public CategoriaDistributionController(
            final CategoriaDistribuidorService categoriaDistribuidorService) {
        this.categoriaDistribuidorService = categoriaDistribuidorService;
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDistribuidorDTO>> getAllCategoriaDistribuidors() {
        return ResponseEntity.ok(categoriaDistribuidorService.findAll());
    }

    @GetMapping("/{idCategoria}")
    public ResponseEntity<CategoriaDistribuidorDTO> getCategoriaDistribuidor(
            @PathVariable(name = "idCategoria") final Integer idCategoria) {
        return ResponseEntity.ok(categoriaDistribuidorService.get(idCategoria));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createCategoriaDistribuidor(
            @RequestBody @Valid final CategoriaDistribuidorDTO categoriaDistribuidorDTO) {
        final Integer createdIdCategoria = categoriaDistribuidorService.create(categoriaDistribuidorDTO);
        return new ResponseEntity<>(createdIdCategoria, HttpStatus.CREATED);
    }

    @PutMapping("/{idCategoria}")
    public ResponseEntity<Integer> updateCategoriaDistribuidor(
            @PathVariable(name = "idCategoria") final Integer idCategoria,
            @RequestBody @Valid final CategoriaDistribuidorDTO categoriaDistribuidorDTO) {
        categoriaDistribuidorService.update(idCategoria, categoriaDistribuidorDTO);
        return ResponseEntity.ok(idCategoria);
    }

    @DeleteMapping("/{idCategoria}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteCategoriaDistribuidor(
            @PathVariable(name = "idCategoria") final Integer idCategoria) {
        categoriaDistribuidorService.delete(idCategoria);
        return ResponseEntity.noContent().build();
    }
}