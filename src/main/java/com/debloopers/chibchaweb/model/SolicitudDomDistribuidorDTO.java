package com.debloopers.chibchaweb.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;


public class SolicitudDomDistribuidorDTO {

    @Size(max = 63)
    @SolicitudDomDistribuidorTldValid
    private String tld;

    @NotNull
    @Size(max = 20)
    private String numeroDocEmpresa;

    @NotNull
    private LocalDate fechaSolicitud;

    @NotNull
    @Size(max = 255)
    private String estadoSolicitud;

    private LocalDate fechaRevision;

    private LocalDate fehcaEnvio;

    @Size(max = 10)
    private String registrador;

    @NotNull
    @Size(max = 20)
    private String nombreTipoDoc;

    @NotNull
    @Size(max = 253)
    private String nombreDominio;

    @Size(max = 20)
    private String admin;

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

    public String getRegistrador() {
        return registrador;
    }

    public void setRegistrador(final String registrador) {
        this.registrador = registrador;
    }

    public String getNombreTipoDoc() {
        return nombreTipoDoc;
    }

    public void setNombreTipoDoc(final String nombreTipoDoc) {
        this.nombreTipoDoc = nombreTipoDoc;
    }

    public String getNombreDominio() {
        return nombreDominio;
    }

    public void setNombreDominio(final String nombreDominio) {
        this.nombreDominio = nombreDominio;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(final String admin) {
        this.admin = admin;
    }

}
