package com.debloopers.chibchaweb.controller;

import com.debloopers.chibchaweb.dto.*;
import com.debloopers.chibchaweb.service.AdministradorService;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/administrador", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdministradorController {

    private final AdministradorService administradorService;

    public AdministradorController(final AdministradorService administradorService) {
        this.administradorService = administradorService;
    }

    @PreAuthorize("hasAuthority('Administrador')")
    @GetMapping
    public ResponseEntity<List<AdministradorDTO>> getAllAdministradors() {
        return ResponseEntity.ok(administradorService.findAll());
    }

    @PreAuthorize("hasAuthority('Administrador')")
    @GetMapping("/{idAdmin}")
    public ResponseEntity<AdministradorDTO> getAdministrador(
            @PathVariable(name = "idAdmin") final Integer idAdmin) {
        return ResponseEntity.ok(administradorService.get(idAdmin));
    }

    @PreAuthorize("hasAuthority('Administrador')")
    @Operation(summary = "Registrar un administrador")
    @PostMapping("/registroAdministrador")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<ResponseDTO> createAdministrador(
            @RequestBody @Valid final AdministradorRegistroRequestDTO administradorDTO) {

        ResponseDTO respuesta = administradorService.create(administradorDTO);

        if (respuesta.isExito()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
    }

    @PreAuthorize("hasAuthority('Administrador')")
    @Operation(summary = "Actualizar un administrador")
    @PutMapping("/{idAdmin}")
    public ResponseEntity<Integer> updateAdministrador(
            @PathVariable(name = "idAdmin") final Integer idAdmin,
            @RequestBody @Valid final AdministradorActualizarDTO administradorDTO) {
        administradorService.update(idAdmin, administradorDTO);
        return ResponseEntity.ok(idAdmin);
    }

    @PreAuthorize("hasAuthority('Administrador')")
    @Operation(summary = "Obtener todos los administradores con su correo y estado")
    @GetMapping("/obtenerAdministradores")
    public ResponseEntity<List<AdministradorConCorreoDTO>> getAllAdministradoresConCorreo() {
        List<AdministradorConCorreoDTO> administradores = administradorService.findAllWithCorreo();
        return ResponseEntity.ok(administradores);
    }

    @PreAuthorize("hasAuthority('Administrador')")
    @DeleteMapping("/{idAdmin}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteAdministrador(
            @PathVariable(name = "idAdmin") final Integer idAdmin) {
        final ReferencedWarning referencedWarning = administradorService.getReferencedWarning(idAdmin);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        administradorService.delete(idAdmin);
        return ResponseEntity.noContent().build();
    }
}