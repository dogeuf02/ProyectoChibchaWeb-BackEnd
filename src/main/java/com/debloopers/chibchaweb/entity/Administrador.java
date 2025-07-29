package com.debloopers.chibchaweb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
public class Administrador {

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
    private Integer idAdmin;

    @Column(nullable = false, length = 50)
    private String nombreAdmin;

    @Column(nullable = false, length = 50)
    private String apellidoAdmin;

    @Column
    private LocalDate fechaNacimientoAdmin;

    @OneToMany(mappedBy = "admin")
    private Set<Usuario> adminUsuarios = new HashSet<>();

    @OneToMany(mappedBy = "admin")
    private Set<SolicitudDominio> adminSolicitudDominios = new HashSet<>();
}