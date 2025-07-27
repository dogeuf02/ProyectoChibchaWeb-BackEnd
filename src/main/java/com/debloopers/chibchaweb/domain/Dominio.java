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
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Dominio {

    @Id
    @Column(nullable = false, updatable = false, length = 253)
    private String nombreDominio;

    @Column
    private String estadoDominio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tld_id", nullable = false)
    private Tld tld;

    @OneToMany(mappedBy = "nombreDominio")
    private Set<SolicitudDomCliente> nombreDominioSolicitudDomClientes = new HashSet<>();

    @OneToMany(mappedBy = "nombreDominio")
    private Set<SolicitudDomDistribuidor> nombreDominioSolicitudDomDistribuidors = new HashSet<>();

}
