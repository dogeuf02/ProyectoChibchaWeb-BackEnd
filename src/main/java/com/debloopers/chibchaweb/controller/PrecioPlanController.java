package com.debloopers.chibchaweb.controller;

import com.debloopers.chibchaweb.dto.PrecioPlanDTO;
import com.debloopers.chibchaweb.service.PrecioPlanService;
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
@RequestMapping(value = "/api/precioPlan", produces = MediaType.APPLICATION_JSON_VALUE)
public class PrecioPlanController {

    private final PrecioPlanService precioPlanService;

    public PrecioPlanController(final PrecioPlanService precioPlanService) {
        this.precioPlanService = precioPlanService;
    }

    @GetMapping
    public ResponseEntity<List<PrecioPlanDTO>> getAllPrecioPlans() {
        return ResponseEntity.ok(precioPlanService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrecioPlanDTO> getPrecioPlan(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(precioPlanService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createPrecioPlan(
            @RequestBody @Valid final PrecioPlanDTO precioPlanDTO) {
        final Long createdId = precioPlanService.create(precioPlanDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updatePrecioPlan(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final PrecioPlanDTO precioPlanDTO) {
        precioPlanService.update(id, precioPlanDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePrecioPlan(@PathVariable(name = "id") final Long id) {
        precioPlanService.delete(id);
        return ResponseEntity.noContent().build();
    }
}