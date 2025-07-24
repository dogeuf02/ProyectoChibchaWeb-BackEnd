package com.debloopers.chibchaweb.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Plan {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Integer idPlan;

    @Column(nullable = false, length = 100)
    private String nombrePlan;

    @OneToMany(mappedBy = "plan")
    private Set<ClienteDirecto> planClienteDirectoes = new HashSet<>();

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

    public Set<ClienteDirecto> getPlanClienteDirectoes() {
        return planClienteDirectoes;
    }

    public void setPlanClienteDirectoes(final Set<ClienteDirecto> planClienteDirectoes) {
        this.planClienteDirectoes = planClienteDirectoes;
    }

}
