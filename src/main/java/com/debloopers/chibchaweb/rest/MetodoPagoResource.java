package com.debloopers.chibchaweb.rest;

import com.debloopers.chibchaweb.model.MetodoPagoDTO;
import com.debloopers.chibchaweb.service.MetodoPagoService;
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
@RequestMapping(value = "/api/metodoPagos", produces = MediaType.APPLICATION_JSON_VALUE)
public class MetodoPagoResource {

    private final MetodoPagoService metodoPagoService;

    public MetodoPagoResource(final MetodoPagoService metodoPagoService) {
        this.metodoPagoService = metodoPagoService;
    }

    @GetMapping
    public ResponseEntity<List<MetodoPagoDTO>> getAllMetodoPagos() {
        return ResponseEntity.ok(metodoPagoService.findAll());
    }

    @GetMapping("/{idMetodoPago}")
    public ResponseEntity<MetodoPagoDTO> getMetodoPago(
            @PathVariable(name = "idMetodoPago") final Integer idMetodoPago) {
        return ResponseEntity.ok(metodoPagoService.get(idMetodoPago));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createMetodoPago(
            @RequestBody @Valid final MetodoPagoDTO metodoPagoDTO) {
        final Integer createdIdMetodoPago = metodoPagoService.create(metodoPagoDTO);
        return new ResponseEntity<>(createdIdMetodoPago, HttpStatus.CREATED);
    }

    @PutMapping("/{idMetodoPago}")
    public ResponseEntity<Integer> updateMetodoPago(
            @PathVariable(name = "idMetodoPago") final Integer idMetodoPago,
            @RequestBody @Valid final MetodoPagoDTO metodoPagoDTO) {
        metodoPagoService.update(idMetodoPago, metodoPagoDTO);
        return ResponseEntity.ok(idMetodoPago);
    }

    @DeleteMapping("/{idMetodoPago}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteMetodoPago(
            @PathVariable(name = "idMetodoPago") final Integer idMetodoPago) {
        metodoPagoService.delete(idMetodoPago);
        return ResponseEntity.noContent().build();
    }

}
