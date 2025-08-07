package com.debloopers.chibchaweb.controller;

import com.debloopers.chibchaweb.dto.PlanAdquiridoDTO;
import com.debloopers.chibchaweb.service.PlanAdquiridoService;
import com.debloopers.chibchaweb.util.ReferencedException;
import com.debloopers.chibchaweb.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping(value = "/api/planAdquirido", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlanAdquiridoController {

    private final PlanAdquiridoService planAdquiridoService;

    public PlanAdquiridoController(final PlanAdquiridoService planAdquiridoService) {
        this.planAdquiridoService = planAdquiridoService;
    }

    @PreAuthorize("hasAuthority('Administrador')")
    @GetMapping
    public ResponseEntity<List<PlanAdquiridoDTO>> getAllPlanAdquiridos() {
        return ResponseEntity.ok(planAdquiridoService.findAll());
    }

    @PreAuthorize("hasAnyAuthority('Administrador','Empleado','Cliente')")
    @GetMapping("/{idPlanAdquirido}")
    public ResponseEntity<PlanAdquiridoDTO> getPlanAdquirido(
            @PathVariable(name = "idPlanAdquirido") final Integer idPlanAdquirido) {
        return ResponseEntity.ok(planAdquiridoService.get(idPlanAdquirido));
    }

    @PreAuthorize("hasAnyAuthority('Administrador','Empleado','Cliente')")
    @Operation(summary = "Obtener los planes adquiridos mediante el id del cliente")
    @GetMapping("/by-cliente/{idCliente}")
    public ResponseEntity<List<PlanAdquiridoDTO>> getByCliente(@PathVariable Integer idCliente) {
        return ResponseEntity.ok(planAdquiridoService.findByClienteId(idCliente));
    }

    @PreAuthorize("hasAnyAuthority('Administrador','Empleado','Cliente')")
    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createPlanAdquirido(
            @RequestBody @Valid final PlanAdquiridoDTO planAdquiridoDTO) {
        final Integer createdIdPlanAdquirido = planAdquiridoService.create(planAdquiridoDTO);
        return new ResponseEntity<>(createdIdPlanAdquirido, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('Administrador','Empleado','Cliente')")
    @PutMapping("/{idPlanAdquirido}")
    public ResponseEntity<Integer> updatePlanAdquirido(
            @PathVariable(name = "idPlanAdquirido") final Integer idPlanAdquirido,
            @RequestBody @Valid final PlanAdquiridoDTO planAdquiridoDTO) {
        planAdquiridoService.update(idPlanAdquirido, planAdquiridoDTO);
        return ResponseEntity.ok(idPlanAdquirido);
    }

    @PreAuthorize("hasAnyAuthority('Administrador')")
    @DeleteMapping("/{idPlanAdquirido}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePlanAdquirido(
            @PathVariable(name = "idPlanAdquirido") final Integer idPlanAdquirido) {
        final ReferencedWarning referencedWarning = planAdquiridoService.getReferencedWarning(idPlanAdquirido);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        planAdquiridoService.delete(idPlanAdquirido);
        return ResponseEntity.noContent().build();
    }
}