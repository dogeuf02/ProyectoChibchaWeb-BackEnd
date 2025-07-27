package com.debloopers.chibchaweb.rest;

import com.debloopers.chibchaweb.model.EmpleadoDTO;
import com.debloopers.chibchaweb.service.EmpleadoService;
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
@RequestMapping(value = "/api/empleados", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmpleadoResource {

    private final EmpleadoService empleadoService;

    public EmpleadoResource(final EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping
    public ResponseEntity<List<EmpleadoDTO>> getAllEmpleados() {
        return ResponseEntity.ok(empleadoService.findAll());
    }

    @GetMapping("/{idEmpleado}")
    public ResponseEntity<EmpleadoDTO> getEmpleado(
            @PathVariable(name = "idEmpleado") final Integer idEmpleado) {
        return ResponseEntity.ok(empleadoService.get(idEmpleado));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createEmpleado(
            @RequestBody @Valid final EmpleadoDTO empleadoDTO) {
        final Integer createdIdEmpleado = empleadoService.create(empleadoDTO);
        return new ResponseEntity<>(createdIdEmpleado, HttpStatus.CREATED);
    }

    @PutMapping("/{idEmpleado}")
    public ResponseEntity<Integer> updateEmpleado(
            @PathVariable(name = "idEmpleado") final Integer idEmpleado,
            @RequestBody @Valid final EmpleadoDTO empleadoDTO) {
        empleadoService.update(idEmpleado, empleadoDTO);
        return ResponseEntity.ok(idEmpleado);
    }

    @DeleteMapping("/{idEmpleado}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteEmpleado(
            @PathVariable(name = "idEmpleado") final Integer idEmpleado) {
        final ReferencedWarning referencedWarning = empleadoService.getReferencedWarning(idEmpleado);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        empleadoService.delete(idEmpleado);
        return ResponseEntity.noContent().build();
    }

}
