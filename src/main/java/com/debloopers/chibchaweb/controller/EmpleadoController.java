package com.debloopers.chibchaweb.controller;

import com.debloopers.chibchaweb.dto.*;
import com.debloopers.chibchaweb.service.EmpleadoService;
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
@RequestMapping(value = "/api/empleado", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(final EmpleadoService empleadoService) {
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

    @GetMapping("/obtenerEmpleados")
    @Operation(summary = "Obtener empleados junto con su correo eletronico")
    public ResponseEntity<List<EmpleadoConCorreoDTO>> getEmpleadosConCorreo() {
        List<EmpleadoConCorreoDTO> empleados = empleadoService.findAllWithCorreo();
        return ResponseEntity.ok(empleados);
    }

    @PostMapping("/registroEmpleado")
    @Operation(summary = "Registro de un nuevo empleado")
    public ResponseEntity<EmpleadoRegistroResponseDTO> createEmpleado(
            @RequestBody @Valid final EmpleadoRegistroRequestDTO dto) {

        EmpleadoRegistroResponseDTO response = empleadoService.create(dto);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @Operation(summary = "Actualizar un empleado")
    @PutMapping("/{idEmpleado}")
    public ResponseEntity<Integer> updateEmpleado(
            @PathVariable(name = "idEmpleado") final Integer idEmpleado,
            @RequestBody @Valid final EmpleadoActualizarDTO empleadoDTO) {
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