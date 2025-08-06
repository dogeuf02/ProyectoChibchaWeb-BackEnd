package com.debloopers.chibchaweb.controller;

import com.debloopers.chibchaweb.dto.PerteneceDominioDTO;
import com.debloopers.chibchaweb.dto.PerteneceDominioRespondeDTO;
import com.debloopers.chibchaweb.service.PerteneceDominioService;
import com.debloopers.chibchaweb.util.ReferencedException;
import com.debloopers.chibchaweb.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping(value = "/api/perteneceDominio", produces = MediaType.APPLICATION_JSON_VALUE)
public class PerteneceDominioController {

    private final PerteneceDominioService perteneceDominioService;

    public PerteneceDominioController(final PerteneceDominioService perteneceDominioService) {
        this.perteneceDominioService = perteneceDominioService;
    }

    @GetMapping
    public ResponseEntity<List<PerteneceDominioDTO>> getAllPerteneceDominios() {
        return ResponseEntity.ok(perteneceDominioService.findAll());
    }

    @GetMapping("/{idPertenece}")
    public ResponseEntity<PerteneceDominioDTO> getPerteneceDominio(
            @PathVariable(name = "idPertenece") final Integer idPertenece) {
        return ResponseEntity.ok(perteneceDominioService.get(idPertenece));
    }

    @Operation(summary = "Obtener registros por cliente")
    @GetMapping("/cliente/{idCliente}")
    public List<PerteneceDominioDTO> obtenerPorIdCliente(@PathVariable Integer idCliente) {
        return perteneceDominioService.obtenerPorIdCliente(idCliente);
    }

    @Operation(summary = "Obtener registros por distribuidor")
    @GetMapping("/distribuidor/{idDistribuidor}")
    public PerteneceDominioRespondeDTO obtenerPorIdDistribuidor(@PathVariable Integer idDistribuidor) {
        return perteneceDominioService.obtenerPorIdDistribuidor(idDistribuidor);
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createPerteneceDominio(
            @RequestBody @Valid final PerteneceDominioDTO perteneceDominioDTO) {
        final Integer createdIdPertenece = perteneceDominioService.create(perteneceDominioDTO);
        return new ResponseEntity<>(createdIdPertenece, HttpStatus.CREATED);
    }

    @PutMapping("/{idPertenece}")
    public ResponseEntity<Integer> updatePerteneceDominio(
            @PathVariable(name = "idPertenece") final Integer idPertenece,
            @RequestBody @Valid final PerteneceDominioDTO perteneceDominioDTO) {
        perteneceDominioService.update(idPertenece, perteneceDominioDTO);
        return ResponseEntity.ok(idPertenece);
    }

    @DeleteMapping("/{idPertenece}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePerteneceDominio(
            @PathVariable(name = "idPertenece") final Integer idPertenece) {
        final ReferencedWarning referencedWarning = perteneceDominioService.getReferencedWarning(idPertenece);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        perteneceDominioService.delete(idPertenece);
        return ResponseEntity.noContent().build();
    }
}