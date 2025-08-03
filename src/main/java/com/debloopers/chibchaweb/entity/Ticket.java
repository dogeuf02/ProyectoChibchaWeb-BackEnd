package com.debloopers.chibchaweb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.OffsetDateTime;
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

    @Column(nullable = false)
    private String nivelComplejidad;

    @Column(nullable = false)
    private String estado;

    @Column
    private OffsetDateTime fechaCreacion;

    @Column
    private OffsetDateTime fechaCierre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "distribuidor_id")
    private Distribuidor distribuidor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private ClienteDirecto cliente;

    @OneToMany(mappedBy = "ticket")
    private Set<HistorialTicket> ticketHistorialTickets = new HashSet<>();

}