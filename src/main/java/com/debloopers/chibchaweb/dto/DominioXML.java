package com.debloopers.chibchaweb.dto;

import jakarta.xml.bind.annotation.XmlElement;

public class DominioXML {

    private String nombreCompleto;
    private String estado;
    private String idTld;

    @XmlElement(name = "Name")
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    @XmlElement(name = "State")
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlElement(name = "Tld")
    public String getIdTld() {
        return idTld;
    }

    public void setIdTld(String idTld) {
        this.idTld = idTld;
    }
}