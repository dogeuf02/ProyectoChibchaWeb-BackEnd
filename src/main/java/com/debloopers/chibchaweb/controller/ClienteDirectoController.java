package com.debloopers.chibchaweb.controller;

import com.debloopers.chibchaweb.dto.*;
import com.debloopers.chibchaweb.service.ClienteDirectoService;
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
@RequestMapping(value = "/api/clienteDirecto", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClienteDirectoController {

    private final ClienteDirectoService clienteDirectoService;

    public ClienteDirectoController(final ClienteDirectoService clienteDirectoService) {
        this.clienteDirectoService = clienteDirectoService;
    }

    @GetMapping
    public ResponseEntity<List<ClienteDirectoDTO>> getAllClienteDirectos() {
        return ResponseEntity.ok(clienteDirectoService.findAll());
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<ClienteDirectoDTO> getClienteDirecto(
            @PathVariable(name = "idCliente") final Integer idCliente) {
        return ResponseEntity.ok(clienteDirectoService.get(idCliente));
    }

    @Operation(summary = "Registrar un cliente")
    @PostMapping("/registroCliente")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<ResponseDTO> create(@RequestBody @Valid ClienteDirectoRegistroRequestDTO clienteDirectoDTO) {
        ResponseDTO response = clienteDirectoService.create(clienteDirectoDTO);

        if (response.isExito()) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Actualizar campos")
    @PutMapping("/{idCliente}")
    public ResponseEntity<Integer> updateClienteDirecto(
            @PathVariable(name = "idCliente") final Integer idCliente,
            @RequestBody @Valid final ClienteDirectoDTO clienteDirectoDTO) {
        clienteDirectoService.update(idCliente, clienteDirectoDTO);
        return ResponseEntity.ok(idCliente);
    }

    @Operation(summary = "Obtener los clientes con su correo y estado")
    @GetMapping("/obtenerClientes")
    public ResponseEntity<List<ClienteDirectoConCorreoDTO>> getAllClientesConCorreo() {
        return ResponseEntity.ok(clienteDirectoService.findAllWithCorreo());
    }

    @DeleteMapping("/{idCliente}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteClienteDirecto(
            @PathVariable(name = "idCliente") final Integer idCliente) {
        final ReferencedWarning referencedWarning = clienteDirectoService.getReferencedWarning(idCliente);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        clienteDirectoService.delete(idCliente);
        return ResponseEntity.noContent().build();
    }
}