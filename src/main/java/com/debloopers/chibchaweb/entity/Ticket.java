package com.debloopers.chibchaweb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Ticket {

    @Id
    @Column(nullable = false, updatable = false, length = 100)
    private String idTicket;

    @Column(nullable = false, length = 100)
    private String asunto;

    @Column(columnDefinition = "text")
    private String descripcion;

    @Column
    private String prioridad;

    @Column(nullable = false)
    private String estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "distribuidor_id")
    private Distribuidor distribuidor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private ClienteDirecto cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;

    @ManyToMany
    @JoinTable(
            name = "HistorialTicketUsuario",
            joinColumns = @JoinColumn(name = "idTicket"),
            inverseJoinColumns = @JoinColumn(name = "idEmpleado")
    )
    private Set<Empleado> historialTicketUsuarioEmpleadoes = new HashSet<>();

}
