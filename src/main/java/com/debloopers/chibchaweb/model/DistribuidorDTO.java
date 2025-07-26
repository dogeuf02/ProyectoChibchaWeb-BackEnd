package com.debloopers.chibchaweb.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class DistribuidorDTO {

    @Size(max = 20)
    @DistribuidorNumeroDocEmpresaValid
    private String numeroDocEmpresa;

    @NotNull
    @Size(max = 255)
    private String nombreEmpresa;

    @NotNull
    @Size(max = 255)
    private String direccionEmpresa;

    @NotNull
    @Size(max = 20)
    private String nombreTipoDoc;

    public String getNumeroDocEmpresa() {
        return numeroDocEmpresa;
    }

    public void setNumeroDocEmpresa(final String numeroDocEmpresa) {
        this.numeroDocEmpresa = numeroDocEmpresa;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(final String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getDireccionEmpresa() {
        return direccionEmpresa;
    }

    public void setDireccionEmpresa(final String direccionEmpresa) {
        this.direccionEmpresa = direccionEmpresa;
    }

    public String getNombreTipoDoc() {
        return nombreTipoDoc;
    }

    public void setNombreTipoDoc(final String nombreTipoDoc) {
        this.nombreTipoDoc = nombreTipoDoc;
    }

}
