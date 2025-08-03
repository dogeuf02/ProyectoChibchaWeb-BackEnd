package com.debloopers.chibchaweb.controller;

import com.debloopers.chibchaweb.dto.FacturaDTO;
import com.debloopers.chibchaweb.service.FacturaService;
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
@RequestMapping(value = "/api/factura", produces = MediaType.APPLICATION_JSON_VALUE)
public class FacturaController {

    private final FacturaService facturaService;

    public FacturaController(final FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    @GetMapping
    public ResponseEntity<List<FacturaDTO>> getAllFacturas() {
        return ResponseEntity.ok(facturaService.findAll());
    }

    @GetMapping("/{idFactura}")
    public ResponseEntity<FacturaDTO> getFactura(
            @PathVariable(name = "idFactura") final Integer idFactura) {
        return ResponseEntity.ok(facturaService.get(idFactura));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createFactura(@RequestBody @Valid final FacturaDTO facturaDTO) {
        final Integer createdIdFactura = facturaService.create(facturaDTO);
        return new ResponseEntity<>(createdIdFactura, HttpStatus.CREATED);
    }

    @PutMapping("/{idFactura}")
    public ResponseEntity<Integer> updateFactura(
            @PathVariable(name = "idFactura") final Integer idFactura,
            @RequestBody @Valid final FacturaDTO facturaDTO) {
        facturaService.update(idFactura, facturaDTO);
        return ResponseEntity.ok(idFactura);
    }

    @DeleteMapping("/{idFactura}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteFactura(
            @PathVariable(name = "idFactura") final Integer idFactura) {
        facturaService.delete(idFactura);
        return ResponseEntity.noContent().build();
    }
}