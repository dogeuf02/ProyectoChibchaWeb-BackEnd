package com.debloopers.chibchaweb.rest;

import com.debloopers.chibchaweb.model.UsuarioDTO;
import com.debloopers.chibchaweb.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioResource {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getById(@PathVariable Integer id) {
        UsuarioDTO dto = usuarioService.findById(id);
        return dto != null
                ? ResponseEntity.ok(dto)
                : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getAll() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @PutMapping("/actualizar/{correo}")
    public ResponseEntity<Boolean> updateByCorreo(@PathVariable String correo, @RequestBody UsuarioDTO usuarioDTO) {
        boolean actualizado = usuarioService.updateByCorreo(correo, usuarioDTO);
        return ResponseEntity.ok(actualizado);
    }
}