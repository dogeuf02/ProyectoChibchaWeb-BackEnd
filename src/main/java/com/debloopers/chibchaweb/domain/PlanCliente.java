package com.debloopers.chibchaweb.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class PlanCliente {

    @Id
    @Column(nullable = false, updatable = false, length = 10)
    private String idPc;

    @Column(nullable = false, length = 100)
    private String nombrePlan;

    public String getIdPc() {
        return idPc;
    }

    public void setIdPc(final String idPc) {
        this.idPc = idPc;
    }

    public String getNombrePlan() {
        return nombrePlan;
    }

    public void setNombrePlan(final String nombrePlan) {
        this.nombrePlan = nombrePlan;
    }

}
