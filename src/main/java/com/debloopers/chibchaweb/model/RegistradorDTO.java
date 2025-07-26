package com.debloopers.chibchaweb.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class RegistradorDTO {

    @Size(max = 10)
    @RegistradorIdRegistradorValid
    private String idRegistrador;

    @NotNull
    @Size(max = 150)
    private String nombreRegistrador;

    public String getIdRegistrador() {
        return idRegistrador;
    }

    public void setIdRegistrador(final String idRegistrador) {
        this.idRegistrador = idRegistrador;
    }

    public String getNombreRegistrador() {
        return nombreRegistrador;
    }

    public void setNombreRegistrador(final String nombreRegistrador) {
        this.nombreRegistrador = nombreRegistrador;
    }

}
