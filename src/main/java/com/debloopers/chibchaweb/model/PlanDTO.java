package com.debloopers.chibchaweb.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class PlanDTO {

    private Integer idPlan;

    @NotNull
    @Size(max = 100)
    private String nombrePlan;

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

}
