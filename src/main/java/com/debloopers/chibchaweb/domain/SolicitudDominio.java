package com.debloopers.chibchaweb.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Getter
@Setter
public class SolicitudDominio {

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
    private Integer idSolicitud;

    @Column(nullable = false)
    private String nombreDominio;

    @Column
    private String estadoDominio;

    @Column(nullable = false)
    private String estadoSolicitud;

    @Column(nullable = false)
    private LocalDate fechaSolicitud;

    @Column
    private LocalDate fechaAprobacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private ClienteDirecto cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "distribuidor_id")
    private Distribuidor distribuidor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tld_id", nullable = false)
    private Tld tld;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Administrador admin;
}
