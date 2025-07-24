package com.debloopers.chibchaweb.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Dominio {

    @Id
    @Column(nullable = false, updatable = false, length = 253)
    private String nombreDominio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tld_id")
    private Tld tld;

}
