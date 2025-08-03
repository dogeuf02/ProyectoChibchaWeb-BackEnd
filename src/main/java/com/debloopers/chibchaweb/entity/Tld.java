package com.debloopers.chibchaweb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Tld {

    @Id
    @Column(nullable = false, updatable = false, length = 63)
    private String tld;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precioTld;

    @OneToMany(mappedBy = "tld")
    private Set<Dominio> tldDominios = new HashSet<>();

}