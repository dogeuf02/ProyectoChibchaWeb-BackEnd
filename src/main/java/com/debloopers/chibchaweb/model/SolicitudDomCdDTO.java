package com.debloopers.chibchaweb.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;


public class SolicitudDomCdDTO {

    @Size(max = 63)
    @SolicitudDomCdTldValid
    private String tld;

    @NotNull
    private LocalDate fechaSolicitud;

    @NotNull
    @Size(max = 50)
    private String estadoSolicitud;

    private LocalDate fechaRevision;

    private LocalDate fechaEnvio;

    @Size(max = 10)
    private String registrador;

    @NotNull
    @Size(max = 253)
    private String nombreDominio;

    @NotNull
    @Size(max = 50)
    private String cliente;

    @Size(max = 20)
    private String admin;

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

    public String getRegistrador() {
        return registrador;
    }

    public void setRegistrador(final String registrador) {
        this.registrador = registrador;
    }

    public String getNombreDominio() {
        return nombreDominio;
    }

    public void setNombreDominio(final String nombreDominio) {
        this.nombreDominio = nombreDominio;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(final String cliente) {
        this.cliente = cliente;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(final String admin) {
        this.admin = admin;
    }

}
