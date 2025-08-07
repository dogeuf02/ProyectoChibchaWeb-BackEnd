package com.debloopers.chibchaweb.controller;

import com.debloopers.chibchaweb.dto.ComisionDTO;
import com.debloopers.chibchaweb.service.ComisionService;
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
@RequestMapping(value = "/api/comision", produces = MediaType.APPLICATION_JSON_VALUE)
public class ComisionController {

    private final ComisionService comisionService;

    public ComisionController(final ComisionService comisionService) {
        this.comisionService = comisionService;
    }

    @PreAuthorize("hasAnyAuthority('Administrador','Distribuidor','Empleado')")
    @GetMapping
    public ResponseEntity<List<ComisionDTO>> getAllComisions() {
        return ResponseEntity.ok(comisionService.findAll());
    }

    @PreAuthorize("hasAnyAuthority('Administrador','Distribuidor','Empleado')")
    @GetMapping("/{idComision}")
    public ResponseEntity<ComisionDTO> getComision(
            @PathVariable(name = "idComision") final Integer idComision) {
        return ResponseEntity.ok(comisionService.get(idComision));
    }

    @PreAuthorize("hasAuthority('Administrador')")
    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createComision(
            @RequestBody @Valid final ComisionDTO comisionDTO) {
        final Integer createdIdComision = comisionService.create(comisionDTO);
        return new ResponseEntity<>(createdIdComision, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('Administrador')")
    @PutMapping("/{idComision}")
    public ResponseEntity<Integer> updateComision(
            @PathVariable(name = "idComision") final Integer idComision,
            @RequestBody @Valid final ComisionDTO comisionDTO) {
        comisionService.update(idComision, comisionDTO);
        return ResponseEntity.ok(idComision);
    }

    @PreAuthorize("hasAuthority('Administrador')")
    @DeleteMapping("/{idComision}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteComision(
            @PathVariable(name = "idComision") final Integer idComision) {
        comisionService.delete(idComision);
        return ResponseEntity.noContent().build();
    }
}