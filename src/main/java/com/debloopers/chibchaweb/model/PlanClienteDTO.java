package com.debloopers.chibchaweb.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PlanClienteDTO {

    @Size(max = 10)
    @PlanClienteIdPcValid
    private String idPc;

    @NotNull
    @Size(max = 100)
    private String nombrePlan;

}
