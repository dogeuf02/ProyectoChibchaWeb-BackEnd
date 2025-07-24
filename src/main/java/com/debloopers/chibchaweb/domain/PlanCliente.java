package com.debloopers.chibchaweb.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class PlanCliente {

    @Id
    @Column(nullable = false, updatable = false, length = 10)
    private String idPc;

    @Column(nullable = false, length = 100)
    private String nombrePlan;

}
