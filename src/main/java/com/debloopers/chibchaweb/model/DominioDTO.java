package com.debloopers.chibchaweb.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class DominioDTO {

    @Size(max = 253)
    @DominioNombreDominioValid
    private String nombreDominio;

    @NotNull
    @Size(max = 63)
    private String tld;

    public String getNombreDominio() {
        return nombreDominio;
    }

    public void setNombreDominio(final String nombreDominio) {
        this.nombreDominio = nombreDominio;
    }

    public String getTld() {
        return tld;
    }

    public void setTld(final String tld) {
        this.tld = tld;
    }

}
