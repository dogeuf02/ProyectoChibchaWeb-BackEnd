package com.debloopers.chibchaweb.rest;

import com.debloopers.chibchaweb.model.AdministradorDTO;
import com.debloopers.chibchaweb.model.AdministradorRegistroRequestDTO;
import com.debloopers.chibchaweb.model.AdministradorRegistroResponseDTO;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/administradors", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdministradorResource {

    private final AdministradorService administradorService;

    public AdministradorResource(final AdministradorService administradorService) {
        this.administradorService = administradorService;
    }

    @GetMapping
    public ResponseEntity<List<AdministradorDTO>> getAllAdministradors() {
        return ResponseEntity.ok(administradorService.findAll());
    }

    @GetMapping("/{idAdmin}")
    public ResponseEntity<AdministradorDTO> getAdministrador(
            @PathVariable(name = "idAdmin") final Integer idAdmin) {
        return ResponseEntity.ok(administradorService.get(idAdmin));
    }

    @Operation(summary = "Registrar un administrador")
    @PostMapping("/registroAdministrador")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<AdministradorRegistroResponseDTO> createAdministrador(
            @RequestBody @Valid final AdministradorRegistroRequestDTO administradorDTO) {

        AdministradorRegistroResponseDTO respuesta = administradorService.create(administradorDTO);

        if (respuesta.isCreado()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
    }


    @PutMapping("/{idAdmin}")
    public ResponseEntity<Integer> updateAdministrador(
            @PathVariable(name = "idAdmin") final Integer idAdmin,
            @RequestBody @Valid final AdministradorDTO administradorDTO) {
        administradorService.update(idAdmin, administradorDTO);
        return ResponseEntity.ok(idAdmin);
    }

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
