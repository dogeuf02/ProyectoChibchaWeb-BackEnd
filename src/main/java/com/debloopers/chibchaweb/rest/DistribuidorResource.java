package com.debloopers.chibchaweb.rest;

import com.debloopers.chibchaweb.model.DistribuidorActualizarDTO;
import com.debloopers.chibchaweb.model.DistribuidorDTO;
import com.debloopers.chibchaweb.model.DistribuidorRegistroRequestDTO;
import com.debloopers.chibchaweb.model.DistribuidorRegistroResponseDTO;
import com.debloopers.chibchaweb.service.DistribuidorService;
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


    @Operation(summary = "Registrar un distribuidor")
    @PostMapping("/registroDistribuidor")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<DistribuidorRegistroResponseDTO> createDistribuidor(
            @RequestBody @Valid final DistribuidorRegistroRequestDTO distribuidorDTO) {

        DistribuidorRegistroResponseDTO response = distribuidorService.create(distribuidorDTO);

        if (response.isExito()) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Actualizar campos excepto el id")
    @PutMapping("/{idDistribuidor}")
    public ResponseEntity<String> updateDistribuidor(
            @PathVariable(name = "idDistribuidor") final Integer idDistribuidor,
            @RequestBody @Valid final DistribuidorActualizarDTO distribuidorDTO) {
        distribuidorService.update(idDistribuidor, distribuidorDTO);
        return ResponseEntity.ok("Distribuidor actualizado correctamente con ID: " + idDistribuidor);
    }

    @DeleteMapping("/{idDistribuidor}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDistribuidor(
            @PathVariable(name = "idDistribuidor") final Integer idDistribuidor) {
        final ReferencedWarning referencedWarning = distribuidorService.getReferencedWarning(idDistribuidor);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        distribuidorService.delete(idDistribuidor);
        return ResponseEntity.noContent().build();
    }

}
