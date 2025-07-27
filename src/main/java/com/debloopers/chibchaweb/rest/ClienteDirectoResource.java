package com.debloopers.chibchaweb.rest;

import com.debloopers.chibchaweb.model.ClienteDirectoActualizarDTO;
import com.debloopers.chibchaweb.model.ClienteDirectoDTO;
import com.debloopers.chibchaweb.model.ClienteDirectoRegistroRequestDTO;
import com.debloopers.chibchaweb.model.ClienteDirectoRegistroResponseDTO;
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
@RequestMapping(value = "/api/clienteDirectos", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClienteDirectoResource {

    private final ClienteDirectoService clienteDirectoService;

    public ClienteDirectoResource(final ClienteDirectoService clienteDirectoService) {
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
    public ResponseEntity<ClienteDirectoRegistroResponseDTO> create(@RequestBody @Valid ClienteDirectoRegistroRequestDTO clienteDirectoDTO) {
        ClienteDirectoRegistroResponseDTO response = clienteDirectoService.create(clienteDirectoDTO);

        if (response.isExito()) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }


    @Operation(summary = "Actualizar campos excepto el id")
    @PutMapping("/{idCliente}")
    public ResponseEntity<String> updateClienteDirecto(
            @PathVariable(name = "idCliente") final Integer idCliente,
            @RequestBody @Valid final ClienteDirectoActualizarDTO clienteDirectoDTO) {
        clienteDirectoService.update(idCliente, clienteDirectoDTO);
        return ResponseEntity.ok("Cliente actualizado correctamente con ID: " + idCliente);
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
