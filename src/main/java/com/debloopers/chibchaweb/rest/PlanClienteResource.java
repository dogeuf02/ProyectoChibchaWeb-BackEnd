package com.debloopers.chibchaweb.rest;

import com.debloopers.chibchaweb.model.PlanClienteDTO;
import com.debloopers.chibchaweb.service.PlanClienteService;
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
@RequestMapping(value = "/api/planClientes", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlanClienteResource {

    private final PlanClienteService planClienteService;

    public PlanClienteResource(final PlanClienteService planClienteService) {
        this.planClienteService = planClienteService;
    }

    @GetMapping
    public ResponseEntity<List<PlanClienteDTO>> getAllPlanClientes() {
        return ResponseEntity.ok(planClienteService.findAll());
    }

    @GetMapping("/{idPc}")
    public ResponseEntity<PlanClienteDTO> getPlanCliente(
            @PathVariable(name = "idPc") final String idPc) {
        return ResponseEntity.ok(planClienteService.get(idPc));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<String> createPlanCliente(
            @RequestBody @Valid final PlanClienteDTO planClienteDTO) {
        final String createdIdPc = planClienteService.create(planClienteDTO);
        return new ResponseEntity<>('"' + createdIdPc + '"', HttpStatus.CREATED);
    }

    @PutMapping("/{idPc}")
    public ResponseEntity<String> updatePlanCliente(@PathVariable(name = "idPc") final String idPc,
            @RequestBody @Valid final PlanClienteDTO planClienteDTO) {
        planClienteService.update(idPc, planClienteDTO);
        return ResponseEntity.ok('"' + idPc + '"');
    }

    @DeleteMapping("/{idPc}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePlanCliente(@PathVariable(name = "idPc") final String idPc) {
        planClienteService.delete(idPc);
        return ResponseEntity.noContent().build();
    }

}
