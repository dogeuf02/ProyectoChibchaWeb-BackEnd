package com.debloopers.chibchaweb.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Tld {

    @Id
    @Column(nullable = false, updatable = false, length = 63)
    private String tld;

    @OneToMany(mappedBy = "tld")
    private Set<Dominio> tldDominios = new HashSet<>();

    public String getTld() {
        return tld;
    }

    public void setTld(final String tld) {
        this.tld = tld;
    }

    public Set<Dominio> getTldDominios() {
        return tldDominios;
    }

    public void setTldDominios(final Set<Dominio> tldDominios) {
        this.tldDominios = tldDominios;
    }

}
