package com.debloopers.chibchaweb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class PlanAdquirido {

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
    private Integer idPlanAdquirido;

    @Column(nullable = false)
    private String estadoPlan;

    @Column(nullable = false)
    private LocalDate fechaCompra;

    @Column(nullable = false)
    private LocalDate fechaExpiracion;

    @Column
    private OffsetDateTime fechaActualizacion;

    @Column(precision = 10, scale = 2)
    private BigDecimal precioPlanAdquirido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private ClienteDirecto cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_cliente_id", nullable = false)
    private PlanCliente planCliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_pago_id", nullable = false)
    private PlanPago planPago;

    @OneToMany(mappedBy = "planAdquirido")
    private Set<Factura> planAdquiridoFacturas = new HashSet<>();

}