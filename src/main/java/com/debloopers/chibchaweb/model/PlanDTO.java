package com.debloopers.chibchaweb.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;


public class PlanDTO {

    private Integer idPlan;

    @NotNull
    @Size(max = 100)
    private String nombrePlan;

    @NotNull
    @Digits(integer = 12, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "90.08")
    private BigDecimal precio;

    public Integer getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(final Integer idPlan) {
        this.idPlan = idPlan;
    }

    public String getNombrePlan() {
        return nombrePlan;
    }

    public void setNombrePlan(final String nombrePlan) {
        this.nombrePlan = nombrePlan;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(final BigDecimal precio) {
        this.precio = precio;
    }

}
