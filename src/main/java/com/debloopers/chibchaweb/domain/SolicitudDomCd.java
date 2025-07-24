package com.debloopers.chibchaweb.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;


@Entity
public class SolicitudDomCd {

    @Id
    @Column(nullable = false, updatable = false, length = 63)
    private String tld;

    @Column(nullable = false)
    private LocalDate fechaSolicitud;

    @Column(nullable = false, length = 50)
    private String estadoSolicitud;

    @Column
    private LocalDate fechaRevision;

    @Column
    private LocalDate fechaEnvio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registrador_id")
    private Registrador registrador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nombre_dominio_id", nullable = false)
    private Dominio nombreDominio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private ClienteDirecto cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Administrador admin;

    public String getTld() {
        return tld;
    }

    public void setTld(final String tld) {
        this.tld = tld;
    }

    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(final LocalDate fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getEstadoSolicitud() {
        return estadoSolicitud;
    }

    public void setEstadoSolicitud(final String estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    public LocalDate getFechaRevision() {
        return fechaRevision;
    }

    public void setFechaRevision(final LocalDate fechaRevision) {
        this.fechaRevision = fechaRevision;
    }

    public LocalDate getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(final LocalDate fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public Registrador getRegistrador() {
        return registrador;
    }

    public void setRegistrador(final Registrador registrador) {
        this.registrador = registrador;
    }

    public Dominio getNombreDominio() {
        return nombreDominio;
    }

    public void setNombreDominio(final Dominio nombreDominio) {
        this.nombreDominio = nombreDominio;
    }

    public ClienteDirecto getCliente() {
        return cliente;
    }

    public void setCliente(final ClienteDirecto cliente) {
        this.cliente = cliente;
    }

    public Administrador getAdmin() {
        return admin;
    }

    public void setAdmin(final Administrador admin) {
        this.admin = admin;
    }

}
