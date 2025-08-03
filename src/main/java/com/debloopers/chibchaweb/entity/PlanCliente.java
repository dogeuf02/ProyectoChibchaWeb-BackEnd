package com.debloopers.chibchaweb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class PlanCliente {

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
    private Integer idPlanCliente;

    @Column(nullable = false, length = 150)
    private String nombrePlanCliente;

    @Column(nullable = false)
    private Integer numeroWebs;

    @Column(nullable = false)
    private Integer numeroBaseDatos;

    @Column(nullable = false)
    private Integer almacenamientoNvme;

    @Column(nullable = false)
    private Integer numeroCuentasCorreo;

    @Column(nullable = false)
    private Boolean creadorWeb;

    @Column(nullable = false)
    private Integer numeroCertificadoSslHttps;

    @Column(nullable = false)
    private Boolean emailMarketing;

    @OneToMany(mappedBy = "planCliente")
    private Set<ClienteDirecto> planClienteClienteDirectoes = new HashSet<>();

    @OneToMany(mappedBy = "planCliente")
    private Set<PlanAdquirido> planClientePlanAdquiridoes = new HashSet<>();

    @OneToMany(mappedBy = "planCliente")
    private Set<PrecioPlan> planClientePrecioPlans = new HashSet<>();

}