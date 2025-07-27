package com.debloopers.chibchaweb.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class RolUsuario {

    @Id
    @Column(nullable = false, updatable = false, length = 50)
    private String nombreRol;

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(final String nombreRol) {
        this.nombreRol = nombreRol;
    }

}
