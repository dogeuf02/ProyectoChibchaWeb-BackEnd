package com.debloopers.chibchaweb.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Application")
public class SolicitudXML {

    private String origen;
    private String fechaSolicitud;
    private ClienteXML cliente;
    private DistribuidorXML distribuidor;
    private DominioXML dominio;

    @XmlElement(name = "Origin")
    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    @XmlElement(name = "DateApplication")
    public String getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(String fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    @XmlElement(name = "Client")
    public ClienteXML getCliente() {
        return cliente;
    }

    public void setCliente(ClienteXML cliente) {
        this.cliente = cliente;
    }

    @XmlElement(name = "Distributor")
    public DistribuidorXML getDistribuidor() {
        return distribuidor;
    }

    public void setDistribuidor(DistribuidorXML distribuidor) {
        this.distribuidor = distribuidor;
    }

    @XmlElement(name = "Domain")
    public DominioXML getDominio() {
        return dominio;
    }

    public void setDominio(DominioXML dominio) {
        this.dominio = dominio;
    }
}