package com.debloopers.chibchaweb.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PlanPagoDTO {

    private Integer idPlanPago;

    @NotNull
    @Size(max = 50)
    private String intervaloPago;

}