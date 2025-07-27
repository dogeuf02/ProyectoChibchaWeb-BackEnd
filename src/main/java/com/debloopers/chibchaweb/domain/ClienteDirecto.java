package com.debloopers.chibchaweb.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
public class ClienteDirecto {

    @Id
    @Column(nullable = false, updatable = false, length = 50)
    private String idCliente;

    @Column(nullable = false, length = 50)
    private String nombreCliente;

    @Column(nullable = false, length = 50)
    private String apellidoCliente;

    @Column(nullable = false, length = 20)
    private String telefono;

    @Column
    private LocalDate fechaNacimientoCliente;

    @OneToMany(mappedBy = "cliente")
    private Set<SolicitudDomCd> clienteSolicitudDomCds = new HashSet<>();

    @OneToMany(mappedBy = "cliente")
    private Set<Ticket> clienteTickets = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @OneToMany(mappedBy = "cliente")
    private Set<Usuario> clienteUsuarios = new HashSet<>();

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(final String idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(final String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getApellidoCliente() {
        return apellidoCliente;
    }

    public void setApellidoCliente(final String apellidoCliente) {
        this.apellidoCliente = apellidoCliente;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(final String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaNacimientoCliente() {
        return fechaNacimientoCliente;
    }

    public void setFechaNacimientoCliente(final LocalDate fechaNacimientoCliente) {
        this.fechaNacimientoCliente = fechaNacimientoCliente;
    }

    public Set<SolicitudDomCd> getClienteSolicitudDomCds() {
        return clienteSolicitudDomCds;
    }

    public void setClienteSolicitudDomCds(final Set<SolicitudDomCd> clienteSolicitudDomCds) {
        this.clienteSolicitudDomCds = clienteSolicitudDomCds;
    }

    public Set<Ticket> getClienteTickets() {
        return clienteTickets;
    }

    public void setClienteTickets(final Set<Ticket> clienteTickets) {
        this.clienteTickets = clienteTickets;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(final Plan plan) {
        this.plan = plan;
    }

    public Set<Usuario> getClienteUsuarios() {
        return clienteUsuarios;
    }

    public void setClienteUsuarios(final Set<Usuario> clienteUsuarios) {
        this.clienteUsuarios = clienteUsuarios;
    }

}
