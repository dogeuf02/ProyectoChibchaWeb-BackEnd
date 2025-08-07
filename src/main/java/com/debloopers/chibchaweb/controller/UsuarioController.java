package com.debloopers.chibchaweb.controller;

import com.debloopers.chibchaweb.dto.ConsultaRolResponseDTO;
import com.debloopers.chibchaweb.dto.UsuarioActualizarDTO;
import com.debloopers.chibchaweb.dto.UsuarioDTO;
import com.debloopers.chibchaweb.service.UsuarioService;
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
@RequestMapping(value = "/api/usuario", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(final UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PreAuthorize("hasAuthority('Administrador')")
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{idUsuario}")
    public ResponseEntity<UsuarioDTO> getUsuario(
            @PathVariable(name = "idUsuario") final Integer idUsuario) {
        return ResponseEntity.ok(usuarioService.get(idUsuario));
    }

    @PreAuthorize("hasAuthority('Administrador')")
    @Operation(summary = "Validar si un correo electronico existe en la base de datos y si corresponde a un cliente o distribuidor, retorna el rol con su ID correspondiente.")
    @GetMapping("/identificarRol")
    public ResponseEntity<ConsultaRolResponseDTO> obtenerRolSolicitante(@RequestParam String correo) {
        ConsultaRolResponseDTO resultado = usuarioService.obtenerRolSolicitante(correo);
        if (resultado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(resultado);
    }

    @PreAuthorize("hasAuthority('Administrador')")
    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createUsuario(@RequestBody @Valid final UsuarioDTO usuarioDTO) {
        final Integer createdIdUsuario = usuarioService.create(usuarioDTO);
        return new ResponseEntity<>(createdIdUsuario, HttpStatus.CREATED);
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Actualizar contraseña y estado de un usuario por correo")
    @PutMapping("/correo/{correoUsuario}")
    public ResponseEntity<String> updateUsuarioPorCorreo(
            @PathVariable(name = "correoUsuario") final String correoUsuario,
            @RequestBody @Valid final UsuarioActualizarDTO usuarioDTO) {

        usuarioService.updateByCorreo(correoUsuario, usuarioDTO);
        return ResponseEntity.ok(correoUsuario);
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Actualizar contraseña y estado de un usuario por ID")
    @PutMapping("/{idUsuario}")
    public ResponseEntity<Integer> updateUsuarioPorId(
            @PathVariable(name = "idUsuario") final Integer idUsuario,
            @RequestBody @Valid final UsuarioActualizarDTO usuarioDTO) {

        usuarioService.updateById(idUsuario, usuarioDTO);
        return ResponseEntity.ok(idUsuario);
    }

    @PreAuthorize("hasAuthority('Administrador')")
    @DeleteMapping("/{idUsuario}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteUsuario(
            @PathVariable(name = "idUsuario") final Integer idUsuario) {
        usuarioService.delete(idUsuario);
        return ResponseEntity.noContent().build();
    }
}