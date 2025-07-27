package com.debloopers.chibchaweb.domain;

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


@Entity
public class Ticket {

    @Id
    @Column(nullable = false, updatable = false, length = 100)
    private String idTicket;

    @Column(length = 20)
    private String numeroDocEmpresa;

    @Column(nullable = false, length = 100)
    private String asunto;

    @Column(columnDefinition = "text")
    private String descripcion;

    @Column
    private String prioridad;

    @Column(nullable = false)
    private String estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private ClienteDirecto cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nombre_tipo_doc_id")
    private Distribuidor nombreTipoDoc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;

    @ManyToMany
    @JoinTable(
            name = "Solucion",
            joinColumns = @JoinColumn(name = "idTicket"),
            inverseJoinColumns = @JoinColumn(name = "idEmpleado")
    )
    private Set<Empleado> solucionEmpleadoes = new HashSet<>();

    public String getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(final String idTicket) {
        this.idTicket = idTicket;
    }

    public String getNumeroDocEmpresa() {
        return numeroDocEmpresa;
    }

    public void setNumeroDocEmpresa(final String numeroDocEmpresa) {
        this.numeroDocEmpresa = numeroDocEmpresa;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(final String asunto) {
        this.asunto = asunto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(final String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(final String prioridad) {
        this.prioridad = prioridad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(final String estado) {
        this.estado = estado;
    }

    public ClienteDirecto getCliente() {
        return cliente;
    }

    public void setCliente(final ClienteDirecto cliente) {
        this.cliente = cliente;
    }

    public Distribuidor getNombreTipoDoc() {
        return nombreTipoDoc;
    }

    public void setNombreTipoDoc(final Distribuidor nombreTipoDoc) {
        this.nombreTipoDoc = nombreTipoDoc;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(final Empleado empleado) {
        this.empleado = empleado;
    }

    public Set<Empleado> getSolucionEmpleadoes() {
        return solucionEmpleadoes;
    }

    public void setSolucionEmpleadoes(final Set<Empleado> solucionEmpleadoes) {
        this.solucionEmpleadoes = solucionEmpleadoes;
    }

}
