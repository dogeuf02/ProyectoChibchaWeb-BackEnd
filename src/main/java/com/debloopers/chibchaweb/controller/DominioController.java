package com.debloopers.chibchaweb.controller;

import com.debloopers.chibchaweb.dto.DominioConNombreTldDTO;
import com.debloopers.chibchaweb.dto.DominioDTO;
import com.debloopers.chibchaweb.entity.Dominio;
import com.debloopers.chibchaweb.service.DominioService;
import com.debloopers.chibchaweb.util.ReferencedException;
import com.debloopers.chibchaweb.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


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

    @Operation(summary = "Buscar dominio por nombre y tld")
    @PostMapping("/buscar")
    public ResponseEntity<DominioDTO> obtenerDominioPorDTO(@RequestBody DominioConNombreTldDTO dominioInfo) {
        DominioDTO dto = dominioService.obtenerDominioPorDTO(dominioInfo);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasAnyAuthority('Administrador','Distribuidor','Cliente')")
    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createDominio(@RequestBody @Valid final DominioDTO dominioDTO) {
        final Integer createdIdDominio = dominioService.create(dominioDTO);
        return new ResponseEntity<>(createdIdDominio, HttpStatus.CREATED);
    }

    @Operation(summary = "Crear dominio y generar precio a partir del No. de caracteres")
    @PostMapping("/crearConPrecio")
    @ResponseStatus(HttpStatus.CREATED)
    public Integer createDominioConPrecioCalculado(@RequestBody @Valid final DominioDTO dominioDTO) {
        return dominioService.createConPrecioCalculado(dominioDTO);
    }

    @PreAuthorize("hasAuthority('Administrador')")
    @PutMapping("/{idDominio}")
    public ResponseEntity<Integer> updateDominio(
            @PathVariable(name = "idDominio") final Integer idDominio,
            @RequestBody @Valid final DominioDTO dominioDTO) {
        dominioService.update(idDominio, dominioDTO);
        return ResponseEntity.ok(idDominio);
    }

    @PreAuthorize("hasAuthority('Administrador')")
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