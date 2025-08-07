package com.debloopers.chibchaweb.controller;

import com.debloopers.chibchaweb.dto.PlanPagoDTO;
import com.debloopers.chibchaweb.service.PlanPagoService;
import com.debloopers.chibchaweb.util.ReferencedException;
import com.debloopers.chibchaweb.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/planPago", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlanPagoController {

    private final PlanPagoService planPagoService;

    public PlanPagoController(final PlanPagoService planPagoService) {
        this.planPagoService = planPagoService;
    }

    @GetMapping
    public ResponseEntity<List<PlanPagoDTO>> getAllPlanPagos() {
        return ResponseEntity.ok(planPagoService.findAll());
    }

    @GetMapping("/{idPlanPago}")
    public ResponseEntity<PlanPagoDTO> getPlanPago(
            @PathVariable(name = "idPlanPago") final Integer idPlanPago) {
        return ResponseEntity.ok(planPagoService.get(idPlanPago));
    }

    @PreAuthorize("hasAuthority('Administrador')")
    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createPlanPago(
            @RequestBody @Valid final PlanPagoDTO planPagoDTO) {
        final Integer createdIdPlanPago = planPagoService.create(planPagoDTO);
        return new ResponseEntity<>(createdIdPlanPago, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('Administrador')")
    @PutMapping("/{idPlanPago}")
    public ResponseEntity<Integer> updatePlanPago(
            @PathVariable(name = "idPlanPago") final Integer idPlanPago,
            @RequestBody @Valid final PlanPagoDTO planPagoDTO) {
        planPagoService.update(idPlanPago, planPagoDTO);
        return ResponseEntity.ok(idPlanPago);
    }

    @PreAuthorize("hasAuthority('Administrador')")
    @DeleteMapping("/{idPlanPago}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePlanPago(
            @PathVariable(name = "idPlanPago") final Integer idPlanPago) {
        final ReferencedWarning referencedWarning = planPagoService.getReferencedWarning(idPlanPago);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        planPagoService.delete(idPlanPago);
        return ResponseEntity.noContent().build();
    }
}