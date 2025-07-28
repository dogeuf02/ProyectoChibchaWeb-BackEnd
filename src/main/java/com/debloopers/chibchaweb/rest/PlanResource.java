package com.debloopers.chibchaweb.rest;

import com.debloopers.chibchaweb.model.PlanDTO;
import com.debloopers.chibchaweb.service.PlanService;
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
@RequestMapping(value = "/api/plan", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlanResource {

    private final PlanService planService;

    public PlanResource(final PlanService planService) {
        this.planService = planService;
    }

    @GetMapping
    public ResponseEntity<List<PlanDTO>> getAllPlans() {
        return ResponseEntity.ok(planService.findAll());
    }

    @GetMapping("/{idPlan}")
    public ResponseEntity<PlanDTO> getPlan(@PathVariable(name = "idPlan") final Integer idPlan) {
        return ResponseEntity.ok(planService.get(idPlan));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createPlan(@RequestBody @Valid final PlanDTO planDTO) {
        final Integer createdIdPlan = planService.create(planDTO);
        return new ResponseEntity<>(createdIdPlan, HttpStatus.CREATED);
    }

    @PutMapping("/{idPlan}")
    public ResponseEntity<Integer> updatePlan(@PathVariable(name = "idPlan") final Integer idPlan,
            @RequestBody @Valid final PlanDTO planDTO) {
        planService.update(idPlan, planDTO);
        return ResponseEntity.ok(idPlan);
    }

    @DeleteMapping("/{idPlan}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePlan(@PathVariable(name = "idPlan") final Integer idPlan) {
        final ReferencedWarning referencedWarning = planService.getReferencedWarning(idPlan);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        planService.delete(idPlan);
        return ResponseEntity.noContent().build();
    }

}
