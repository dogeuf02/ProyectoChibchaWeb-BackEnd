package com.debloopers.chibchaweb.dto;

import jakarta.xml.bind.annotation.XmlElement;

public class DistribuidorXML {

    private String nombreEmpresa;
    private String numeroDocumento;
    private String direccionEmpresa;
    private String correo;

    @XmlElement(name = "CompanyName")
    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    @XmlElement(name = "DocumentNumber")
    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    @XmlElement(name = "Address")
    public String getDireccionEmpresa() {
        return direccionEmpresa;
    }

    public void setDireccionEmpresa(String direccionEmpresa) {
        this.direccionEmpresa = direccionEmpresa;
    }

    @XmlElement(name = "Email")
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}