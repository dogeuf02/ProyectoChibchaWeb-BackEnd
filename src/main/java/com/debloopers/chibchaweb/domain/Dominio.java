package com.debloopers.chibchaweb.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Dominio {

    @Id
    @Column(nullable = false, updatable = false, length = 253)
    private String nombreDominio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tld_id", nullable = false)
    private Tld tld;

    @OneToMany(mappedBy = "nombreDominio")
    private Set<SolicitudDomCd> nombreDominioSolicitudDomCds = new HashSet<>();

    @OneToMany(mappedBy = "nombreDominio")
    private Set<SolicitudDomDistribuidor> nombreDominioSolicitudDomDistribuidors = new HashSet<>();

    public String getNombreDominio() {
        return nombreDominio;
    }

    public void setNombreDominio(final String nombreDominio) {
        this.nombreDominio = nombreDominio;
    }

    public Tld getTld() {
        return tld;
    }

    public void setTld(final Tld tld) {
        this.tld = tld;
    }

    public Set<SolicitudDomCd> getNombreDominioSolicitudDomCds() {
        return nombreDominioSolicitudDomCds;
    }

    public void setNombreDominioSolicitudDomCds(
            final Set<SolicitudDomCd> nombreDominioSolicitudDomCds) {
        this.nombreDominioSolicitudDomCds = nombreDominioSolicitudDomCds;
    }

    public Set<SolicitudDomDistribuidor> getNombreDominioSolicitudDomDistribuidors() {
        return nombreDominioSolicitudDomDistribuidors;
    }

    public void setNombreDominioSolicitudDomDistribuidors(
            final Set<SolicitudDomDistribuidor> nombreDominioSolicitudDomDistribuidors) {
        this.nombreDominioSolicitudDomDistribuidors = nombreDominioSolicitudDomDistribuidors;
    }

}
