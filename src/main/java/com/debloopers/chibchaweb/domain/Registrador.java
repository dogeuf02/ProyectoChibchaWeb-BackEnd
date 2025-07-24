package com.debloopers.chibchaweb.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Registrador {

    @Id
    @Column(nullable = false, updatable = false, length = 20)
    private String idRegistrador;

    @Column(nullable = false, length = 100)
    private String nombreRegistrador;

    @Column(nullable = false)
    private String correoRegistrador;

}
