package com.debloopers.chibchaweb.rest;

import com.debloopers.chibchaweb.model.RegistradorDTO;
import com.debloopers.chibchaweb.service.RegistradorService;
import com.debloopers.chibchaweb.util.ReferencedException;
import com.debloopers.chibchaweb.util.ReferencedWarning;
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
@RequestMapping(value = "/api/registradors", produces = MediaType.APPLICATION_JSON_VALUE)
public class RegistradorResource {

    private final RegistradorService registradorService;

    public RegistradorResource(final RegistradorService registradorService) {
        this.registradorService = registradorService;
    }

    @GetMapping
    public ResponseEntity<List<RegistradorDTO>> getAllRegistradors() {
        return ResponseEntity.ok(registradorService.findAll());
    }

    @GetMapping("/{idRegistrador}")
    public ResponseEntity<RegistradorDTO> getRegistrador(
            @PathVariable(name = "idRegistrador") final Integer idRegistrador) {
        return ResponseEntity.ok(registradorService.get(idRegistrador));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createRegistrador(
            @RequestBody @Valid final RegistradorDTO registradorDTO) {
        final Integer createdIdRegistrador = registradorService.create(registradorDTO);
        return new ResponseEntity<>(createdIdRegistrador, HttpStatus.CREATED);
    }

    @PutMapping("/{idRegistrador}")
    public ResponseEntity<Integer> updateRegistrador(
            @PathVariable(name = "idRegistrador") final Integer idRegistrador,
            @RequestBody @Valid final RegistradorDTO registradorDTO) {
        registradorService.update(idRegistrador, registradorDTO);
        return ResponseEntity.ok(idRegistrador);
    }

    @DeleteMapping("/{idRegistrador}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteRegistrador(
            @PathVariable(name = "idRegistrador") final Integer idRegistrador) {
        final ReferencedWarning referencedWarning = registradorService.getReferencedWarning(idRegistrador);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        registradorService.delete(idRegistrador);
        return ResponseEntity.noContent().build();
    }

}
