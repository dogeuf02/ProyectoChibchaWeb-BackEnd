package com.debloopers.chibchaweb.controller;

import com.debloopers.chibchaweb.dto.CategoriaDistribuidorDTO;
import com.debloopers.chibchaweb.service.CategoriaDistribuidorService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAnyAuthority('Administrador','Distribuidor','Empleado')")
    @GetMapping
    public ResponseEntity<List<CategoriaDistribuidorDTO>> getAllCategoriaDistribuidors() {
        return ResponseEntity.ok(categoriaDistribuidorService.findAll());
    }

    @PreAuthorize("hasAnyAuthority('Administrador','Distribuidor','Empleado')")
    @GetMapping("/{idCategoria}")
    public ResponseEntity<CategoriaDistribuidorDTO> getCategoriaDistribuidor(
            @PathVariable(name = "idCategoria") final Integer idCategoria) {
        return ResponseEntity.ok(categoriaDistribuidorService.get(idCategoria));
    }

    @PreAuthorize("hasAuthority('Administrador')")
    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createCategoriaDistribuidor(
            @RequestBody @Valid final CategoriaDistribuidorDTO categoriaDistribuidorDTO) {
        final Integer createdIdCategoria = categoriaDistribuidorService.create(categoriaDistribuidorDTO);
        return new ResponseEntity<>(createdIdCategoria, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('Administrador')")
    @PutMapping("/{idCategoria}")
    public ResponseEntity<Integer> updateCategoriaDistribuidor(
            @PathVariable(name = "idCategoria") final Integer idCategoria,
            @RequestBody @Valid final CategoriaDistribuidorDTO categoriaDistribuidorDTO) {
        categoriaDistribuidorService.update(idCategoria, categoriaDistribuidorDTO);
        return ResponseEntity.ok(idCategoria);
    }

    @PreAuthorize("hasAuthority('Administrador')")
    @DeleteMapping("/{idCategoria}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteCategoriaDistribuidor(
            @PathVariable(name = "idCategoria") final Integer idCategoria) {
        categoriaDistribuidorService.delete(idCategoria);
        return ResponseEntity.noContent().build();
    }
}