package com.debloopers.chibchaweb.controller;

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
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/usuario", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(final UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<UsuarioDTO> getUsuario(
            @PathVariable(name = "idUsuario") final Integer idUsuario) {
        return ResponseEntity.ok(usuarioService.get(idUsuario));
    }

    @Operation(summary = "Validar si un correo electronico existe en la base de datos y si corresponde a un cliente o distribuidor")
    @GetMapping("/es-solicitante")
    public boolean esClienteODistribuidor(@RequestParam String correo) {
        return usuarioService.esClienteODistribuidor(correo);
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createUsuario(@RequestBody @Valid final UsuarioDTO usuarioDTO) {
        final Integer createdIdUsuario = usuarioService.create(usuarioDTO);
        return new ResponseEntity<>(createdIdUsuario, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar contraseña y estado de un usuario por correo")
    @PutMapping("/correo/{correoUsuario}")
    public ResponseEntity<String> updateUsuarioPorCorreo(
            @PathVariable(name = "correoUsuario") final String correoUsuario,
            @RequestBody @Valid final UsuarioActualizarDTO usuarioDTO) {

        usuarioService.updateByCorreo(correoUsuario, usuarioDTO);
        return ResponseEntity.ok(correoUsuario);
    }

    @Operation(summary = "Actualizar contraseña y estado de un usuario por ID")
    @PutMapping("/{idUsuario}")
    public ResponseEntity<Integer> updateUsuarioPorId(
            @PathVariable(name = "idUsuario") final Integer idUsuario,
            @RequestBody @Valid final UsuarioActualizarDTO usuarioDTO) {

        usuarioService.updateById(idUsuario, usuarioDTO);
        return ResponseEntity.ok(idUsuario);
    }

    @DeleteMapping("/{idUsuario}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteUsuario(
            @PathVariable(name = "idUsuario") final Integer idUsuario) {
        usuarioService.delete(idUsuario);
        return ResponseEntity.noContent().build();
    }
}