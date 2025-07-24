package com.debloopers.chibchaweb.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Empleado {

    @Id
    @Column(nullable = false, updatable = false, length = 10)
    private String idEmpleado;

    @Column(nullable = false, length = 50)
    private String nombreEmpleado;

    @Column(nullable = false, length = 50)
    private String apellidoEmpleado;

    @Column(nullable = false, length = 100)
    private String cargoEmpleado;

    @Column(nullable = false, length = 50)
    private String usuarioEmpelado;

    @Column(nullable = false, length = 50)
    private String contrasenaEmpleado;

    @ManyToMany(mappedBy = "solucionEmpleadoes")
    private Set<Ticket> solucionTickets = new HashSet<>();

    @OneToMany(mappedBy = "empleado")
    private Set<Ticket> empleadoTickets = new HashSet<>();

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(final String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(final String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getApellidoEmpleado() {
        return apellidoEmpleado;
    }

    public void setApellidoEmpleado(final String apellidoEmpleado) {
        this.apellidoEmpleado = apellidoEmpleado;
    }

    public String getCargoEmpleado() {
        return cargoEmpleado;
    }

    public void setCargoEmpleado(final String cargoEmpleado) {
        this.cargoEmpleado = cargoEmpleado;
    }

    public String getUsuarioEmpelado() {
        return usuarioEmpelado;
    }

    public void setUsuarioEmpelado(final String usuarioEmpelado) {
        this.usuarioEmpelado = usuarioEmpelado;
    }

    public String getContrasenaEmpleado() {
        return contrasenaEmpleado;
    }

    public void setContrasenaEmpleado(final String contrasenaEmpleado) {
        this.contrasenaEmpleado = contrasenaEmpleado;
    }

    public Set<Ticket> getSolucionTickets() {
        return solucionTickets;
    }

    public void setSolucionTickets(final Set<Ticket> solucionTickets) {
        this.solucionTickets = solucionTickets;
    }

    public Set<Ticket> getEmpleadoTickets() {
        return empleadoTickets;
    }

    public void setEmpleadoTickets(final Set<Ticket> empleadoTickets) {
        this.empleadoTickets = empleadoTickets;
    }

}
