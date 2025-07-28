package com.debloopers.chibchaweb.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;


@Entity
public class Registrador {

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
    private Integer idRegistrador;

    @Column(nullable = false, length = 150)
    private String nombreRegistrador;

    @Column(nullable = false, length = 150)
    private String correoRegistrador;

    public Integer getIdRegistrador() {
        return idRegistrador;
    }

    public void setIdRegistrador(final Integer idRegistrador) {
        this.idRegistrador = idRegistrador;
    }

    public String getNombreRegistrador() {
        return nombreRegistrador;
    }

    public void setNombreRegistrador(final String nombreRegistrador) {
        this.nombreRegistrador = nombreRegistrador;
    }

    public String getCorreoRegistrador() {
        return correoRegistrador;
    }

    public void setCorreoRegistrador(final String correoRegistrador) {
        this.correoRegistrador = correoRegistrador;
    }

}