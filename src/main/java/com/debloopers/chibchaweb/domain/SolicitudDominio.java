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
import java.time.LocalDate;


@Entity
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

    public Integer getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(final Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getNombreDominio() {
        return nombreDominio;
    }

    public void setNombreDominio(final String nombreDominio) {
        this.nombreDominio = nombreDominio;
    }

    public String getEstadoDominio() {
        return estadoDominio;
    }

    public void setEstadoDominio(final String estadoDominio) {
        this.estadoDominio = estadoDominio;
    }

    public String getEstadoSolicitud() {
        return estadoSolicitud;
    }

    public void setEstadoSolicitud(final String estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(final LocalDate fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public LocalDate getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(final LocalDate fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    public ClienteDirecto getCliente() {
        return cliente;
    }

    public void setCliente(final ClienteDirecto cliente) {
        this.cliente = cliente;
    }

    public Distribuidor getDistribuidor() {
        return distribuidor;
    }

    public void setDistribuidor(final Distribuidor distribuidor) {
        this.distribuidor = distribuidor;
    }

    public Tld getTld() {
        return tld;
    }

    public void setTld(final Tld tld) {
        this.tld = tld;
    }

    public Administrador getAdmin() {
        return admin;
    }

    public void setAdmin(final Administrador admin) {
        this.admin = admin;
    }

}
