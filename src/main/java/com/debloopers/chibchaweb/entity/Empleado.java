package com.debloopers.chibchaweb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Empleado {

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
    private Integer idEmpleado;

    @Column(nullable = false, length = 50)
    private String nombreEmpleado;

    @Column(nullable = false, length = 50)
    private String apellidoEmpleado;

    @Column(nullable = false)
    private String cargoEmpleado;

    @OneToMany(mappedBy = "empleado")
    private Set<Usuario> empleadoUsuarios = new HashSet<>();

    @OneToMany(mappedBy = "empleadoRealizador")
    private Set<HistorialTicket> empleadoRealizadorHistorialTickets = new HashSet<>();

    @OneToMany(mappedBy = "empleadoReceptor")
    private Set<HistorialTicket> empleadoReceptorHistorialTickets = new HashSet<>();

}