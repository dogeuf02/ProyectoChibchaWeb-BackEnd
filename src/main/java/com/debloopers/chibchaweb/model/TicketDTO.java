package com.debloopers.chibchaweb.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;


public class TicketDTO {

    @Size(max = 100)
    @TicketIdTicketValid
    private String idTicket;

    @Size(max = 20)
    private String numeroDocEmpresa;

    @NotNull
    @Size(max = 100)
    private String asunto;

    private String descripcion;

    @Size(max = 30)
    private String prioridad;

    @NotNull
    @Size(max = 30)
    private String estado;

    private List<String> solucionEmpleadoes;

    @Size(max = 50)
    private String cliente;

    @Size(max = 20)
    private String nombreTipoDoc;

    @Size(max = 10)
    private String empleado;

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

    public List<String> getSolucionEmpleadoes() {
        return solucionEmpleadoes;
    }

    public void setSolucionEmpleadoes(final List<String> solucionEmpleadoes) {
        this.solucionEmpleadoes = solucionEmpleadoes;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(final String cliente) {
        this.cliente = cliente;
    }

    public String getNombreTipoDoc() {
        return nombreTipoDoc;
    }

    public void setNombreTipoDoc(final String nombreTipoDoc) {
        this.nombreTipoDoc = nombreTipoDoc;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(final String empleado) {
        this.empleado = empleado;
    }

}
