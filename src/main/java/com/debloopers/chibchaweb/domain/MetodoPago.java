package com.debloopers.chibchaweb.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;


@Entity
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
