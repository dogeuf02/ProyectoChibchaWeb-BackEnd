package com.debloopers.chibchaweb.rest;

import com.debloopers.chibchaweb.model.ClienteDirectoDTO;
import com.debloopers.chibchaweb.service.ClienteDirectoService;
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
            @PathVariable(name = "idCliente") final String idCliente) {
        return ResponseEntity.ok(clienteDirectoService.get(idCliente));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<String> createClienteDirecto(
            @RequestBody @Valid final ClienteDirectoDTO clienteDirectoDTO) {
        final String createdIdCliente = clienteDirectoService.create(clienteDirectoDTO);
        return new ResponseEntity<>('"' + createdIdCliente + '"', HttpStatus.CREATED);
    }

    @PutMapping("/{idCliente}")
    public ResponseEntity<String> updateClienteDirecto(
            @PathVariable(name = "idCliente") final String idCliente,
            @RequestBody @Valid final ClienteDirectoDTO clienteDirectoDTO) {
        clienteDirectoService.update(idCliente, clienteDirectoDTO);
        return ResponseEntity.ok('"' + idCliente + '"');
    }

    @DeleteMapping("/{idCliente}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteClienteDirecto(
            @PathVariable(name = "idCliente") final String idCliente) {
        final ReferencedWarning referencedWarning = clienteDirectoService.getReferencedWarning(idCliente);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        clienteDirectoService.delete(idCliente);
        return ResponseEntity.noContent().build();
    }

}
