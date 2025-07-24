package com.debloopers.chibchaweb.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;


@Entity
public class SolicitudDomDistribuidor {

    @Id
    @Column(nullable = false, updatable = false, length = 63)
    private String tld;

    @Column(nullable = false, length = 20)
    private String numeroDocEmpresa;

    @Column(nullable = false)
    private LocalDate fechaSolicitud;

    @Column(nullable = false, length = 30)
    private String estadoSolicitud;

    @Column
    private LocalDate fechaRevision;

    @Column
    private LocalDate fehcaEnvio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registrador_id")
    private Registrador registrador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nombre_tipo_doc_id", nullable = false)
    private Distribuidor nombreTipoDoc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nombre_dominio_id", nullable = false)
    private Dominio nombreDominio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Administrador admin;

    public String getTld() {
        return tld;
    }

    public void setTld(final String tld) {
        this.tld = tld;
    }

    public String getNumeroDocEmpresa() {
        return numeroDocEmpresa;
    }

    public void setNumeroDocEmpresa(final String numeroDocEmpresa) {
        this.numeroDocEmpresa = numeroDocEmpresa;
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

    public LocalDate getFehcaEnvio() {
        return fehcaEnvio;
    }

    public void setFehcaEnvio(final LocalDate fehcaEnvio) {
        this.fehcaEnvio = fehcaEnvio;
    }

    public Registrador getRegistrador() {
        return registrador;
    }

    public void setRegistrador(final Registrador registrador) {
        this.registrador = registrador;
    }

    public Distribuidor getNombreTipoDoc() {
        return nombreTipoDoc;
    }

    public void setNombreTipoDoc(final Distribuidor nombreTipoDoc) {
        this.nombreTipoDoc = nombreTipoDoc;
    }

    public Dominio getNombreDominio() {
        return nombreDominio;
    }

    public void setNombreDominio(final Dominio nombreDominio) {
        this.nombreDominio = nombreDominio;
    }

    public Administrador getAdmin() {
        return admin;
    }

    public void setAdmin(final Administrador admin) {
        this.admin = admin;
    }

}
