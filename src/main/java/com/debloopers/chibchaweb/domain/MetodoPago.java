package com.debloopers.chibchaweb.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class MetodoPago {

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
    private Integer idMetodoPago;

    @Column(nullable = false, length = 100)
    private String nombreTitular;

    @Column(nullable = false, length = 20)
    private String marcaTarjeta;

    @Column(nullable = false, length = 16)
    private String numeroTarjeta;

    @Column(nullable = false, length = 10)
    private String tipoTarjeta;

    @Column(nullable = false, length = 3)
    private String cvv;

    @Column(nullable = false, length = 2)
    private String mesExpiracion;

    @Column(nullable = false, length = 2)
    private String anoExpiracion;

}
