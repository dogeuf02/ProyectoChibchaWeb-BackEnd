package com.debloopers.chibchaweb.rest;

import com.debloopers.chibchaweb.model.DistribuidorDTO;
import com.debloopers.chibchaweb.service.DistribuidorService;
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
@RequestMapping(value = "/api/distribuidors", produces = MediaType.APPLICATION_JSON_VALUE)
public class DistribuidorResource {

    private final DistribuidorService distribuidorService;

    public DistribuidorResource(final DistribuidorService distribuidorService) {
        this.distribuidorService = distribuidorService;
    }

    @GetMapping
    public ResponseEntity<List<DistribuidorDTO>> getAllDistribuidors() {
        return ResponseEntity.ok(distribuidorService.findAll());
    }

    @GetMapping("/{idDistribuidor}")
    public ResponseEntity<DistribuidorDTO> getDistribuidor(
            @PathVariable(name = "idDistribuidor") final Integer idDistribuidor) {
        return ResponseEntity.ok(distribuidorService.get(idDistribuidor));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createDistribuidor(
            @RequestBody @Valid final DistribuidorDTO distribuidorDTO) {
        final Integer createdIdDistribuidor = distribuidorService.create(distribuidorDTO);
        return new ResponseEntity<>(createdIdDistribuidor, HttpStatus.CREATED);
    }

    @PutMapping("/{idDistribuidor}")
    public ResponseEntity<Integer> updateDistribuidor(
            @PathVariable(name = "idDistribuidor") final Integer idDistribuidor,
            @RequestBody @Valid final DistribuidorDTO distribuidorDTO) {
        distribuidorService.update(idDistribuidor, distribuidorDTO);
        return ResponseEntity.ok(idDistribuidor);
    }

    @DeleteMapping("/{idDistribuidor}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDistribuidor(
            @PathVariable(name = "idDistribuidor") final Integer idDistribuidor) {
        distribuidorService.delete(idDistribuidor);
        return ResponseEntity.noContent().build();
    }

}
