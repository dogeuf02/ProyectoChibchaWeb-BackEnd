package com.debloopers.chibchaweb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Dominio {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Integer idDominio;

    @Column(nullable = false, length = 263)
    private String nombreDominio;

    @Column(precision = 10, scale = 2)
    private BigDecimal precioDominio;

    @Column
    private String estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tld_id", nullable = false)
    private Tld tld;

    @OneToMany(mappedBy = "dominio")
    private Set<SolicitudDominio> dominioSolicitudDominios = new HashSet<>();

    @OneToMany(mappedBy = "dominio")
    private Set<PerteneceDominio> dominioPerteneceDominios = new HashSet<>();

}