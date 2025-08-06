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
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class MedioPago {

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
    private Integer idMedioPago;

    @Column(nullable = false)
    private String tipoMedioPago;

    @Column(nullable = false, length = 100)
    private String nombreTitular;

    @Column(length = 100)
    private String numeroTarjetaCuenta;

    @Column(length = 100)
    private String correoPse;

    @Column
    private OffsetDateTime fechaRegistro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private ClienteDirecto cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "distribuidor_id")
    private Distribuidor distribuidor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "banco_id")
    private Banco banco;

    @OneToMany(mappedBy = "medioPago")
    private Set<Factura> medioPagoFacturas = new HashSet<>();

    @OneToMany(mappedBy = "medioPago")
    private Set<Comision> medioPagoComisions = new HashSet<>();

}