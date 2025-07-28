package com.debloopers.chibchaweb.rest;

import com.debloopers.chibchaweb.model.UsuarioActualizarDTO;
import com.debloopers.chibchaweb.model.UsuarioDTO;
import com.debloopers.chibchaweb.service.UsuarioService;
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
@RequestMapping(value = "/api/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioResource {

    private final UsuarioService usuarioService;

    public UsuarioResource(final UsuarioService usuarioService) {
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

        usuarioService.update(correoUsuario, usuarioDTO);
        return ResponseEntity.ok(correoUsuario);
    }

    @DeleteMapping("/{idUsuario}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteUsuario(
            @PathVariable(name = "idUsuario") final Integer idUsuario) {
        usuarioService.delete(idUsuario);
        return ResponseEntity.noContent().build();
    }

}
