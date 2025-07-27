package com.debloopers.chibchaweb.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;


@Entity
public class Usuario {

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
    private Integer idUsuario;

    @Column(nullable = false)
    private String rolUsuario;

    @Column(nullable = false, length = 150)
    private String correoUsuario;

    @Column(nullable = false, length = 150)
    private String contrasena;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private ClienteDirecto cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Administrador admin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registrador_id")
    private Registrador registrador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(final Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getRolUsuario() {
        return rolUsuario;
    }

    public void setRolUsuario(final String rolUsuario) {
        this.rolUsuario = rolUsuario;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public void setCorreoUsuario(final String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(final String contrasena) {
        this.contrasena = contrasena;
    }

    public ClienteDirecto getCliente() {
        return cliente;
    }

    public void setCliente(final ClienteDirecto cliente) {
        this.cliente = cliente;
    }

    public Administrador getAdmin() {
        return admin;
    }

    public void setAdmin(final Administrador admin) {
        this.admin = admin;
    }

    public Registrador getRegistrador() {
        return registrador;
    }

    public void setRegistrador(final Registrador registrador) {
        this.registrador = registrador;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(final Empleado empleado) {
        this.empleado = empleado;
    }

}
