package com.debloopers.chibchaweb.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Domain")
public class DominioXML {

    private Integer idDominio;
    private String nombreDominio;
    private String estado;

    @XmlElement(name = "Id")
    public Integer getIdDominio() {
        return idDominio;
    }

    public void setIdDominio(Integer idDominio) {
        this.idDominio = idDominio;
    }

    @XmlElement(name = "Name")
    public String getNombreDominio() {
        return nombreDominio;
    }

    public void setNombreDominio(String nombreDominio) {
        this.nombreDominio = nombreDominio;
    }

    @XmlElement(name = "Status")
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}