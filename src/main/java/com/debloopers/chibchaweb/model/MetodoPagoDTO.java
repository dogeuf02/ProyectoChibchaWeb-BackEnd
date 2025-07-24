package com.debloopers.chibchaweb.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class MetodoPagoDTO {

    private Integer idMetodoPago;

    @NotNull
    @Size(max = 100)
    private String nombreTitular;

    @NotNull
    @Size(max = 20)
    private String marcaTarjeta;

    @NotNull
    @Size(max = 16)
    private String numeroTarjeta;

    @NotNull
    @Size(max = 10)
    private String tipoTarjeta;

    @NotNull
    @Size(max = 3)
    private String cvv;

    @NotNull
    @Size(max = 2)
    private String mesExpiracion;

    @NotNull
    @Size(max = 2)
    private String anoExpiracion;

    public Integer getIdMetodoPago() {
        return idMetodoPago;
    }

    public void setIdMetodoPago(final Integer idMetodoPago) {
        this.idMetodoPago = idMetodoPago;
    }

    public String getNombreTitular() {
        return nombreTitular;
    }

    public void setNombreTitular(final String nombreTitular) {
        this.nombreTitular = nombreTitular;
    }

    public String getMarcaTarjeta() {
        return marcaTarjeta;
    }

    public void setMarcaTarjeta(final String marcaTarjeta) {
        this.marcaTarjeta = marcaTarjeta;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(final String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(final String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(final String cvv) {
        this.cvv = cvv;
    }

    public String getMesExpiracion() {
        return mesExpiracion;
    }

    public void setMesExpiracion(final String mesExpiracion) {
        this.mesExpiracion = mesExpiracion;
    }

    public String getAnoExpiracion() {
        return anoExpiracion;
    }

    public void setAnoExpiracion(final String anoExpiracion) {
        this.anoExpiracion = anoExpiracion;
    }

}
