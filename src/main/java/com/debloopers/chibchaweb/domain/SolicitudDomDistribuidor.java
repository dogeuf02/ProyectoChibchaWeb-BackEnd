package com.debloopers.chibchaweb.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class SolicitudDomDistribuidor {

    @Id
    @Column(nullable = false, updatable = false, length = 63)
    private String tld;

    @Column(nullable = false)
    private LocalDate fechaSolicitud;

    @Column(nullable = false)
    private String estadoSolicitud;

    @Column
    private LocalDate fechaRevision;

    @Column
    private LocalDate fechaEnvio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "distribuidor_id", nullable = false)
    private Distribuidor distribuidor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nombre_dominio_id", nullable = false)
    private Dominio nombreDominio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Administrador admin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registrador_id")
    private Registrador registrador;

}
