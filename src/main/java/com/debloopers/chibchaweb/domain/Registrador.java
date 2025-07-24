package com.debloopers.chibchaweb.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Registrador {

    @Id
    @Column(nullable = false, updatable = false, length = 10)
    private String idRegistrador;

    @Column(nullable = false, length = 150)
    private String nombreRegistrador;

    @Column(nullable = false, length = 150)
    private String correoRegistrador;

    @OneToMany(mappedBy = "registrador")
    private Set<SolicitudDomCd> registradorSolicitudDomCds = new HashSet<>();

    @OneToMany(mappedBy = "registrador")
    private Set<SolicitudDomDistribuidor> registradorSolicitudDomDistribuidors = new HashSet<>();

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

    public String getCorreoRegistrador() {
        return correoRegistrador;
    }

    public void setCorreoRegistrador(final String correoRegistrador) {
        this.correoRegistrador = correoRegistrador;
    }

    public Set<SolicitudDomCd> getRegistradorSolicitudDomCds() {
        return registradorSolicitudDomCds;
    }

    public void setRegistradorSolicitudDomCds(
            final Set<SolicitudDomCd> registradorSolicitudDomCds) {
        this.registradorSolicitudDomCds = registradorSolicitudDomCds;
    }

    public Set<SolicitudDomDistribuidor> getRegistradorSolicitudDomDistribuidors() {
        return registradorSolicitudDomDistribuidors;
    }

    public void setRegistradorSolicitudDomDistribuidors(
            final Set<SolicitudDomDistribuidor> registradorSolicitudDomDistribuidors) {
        this.registradorSolicitudDomDistribuidors = registradorSolicitudDomDistribuidors;
    }

}
