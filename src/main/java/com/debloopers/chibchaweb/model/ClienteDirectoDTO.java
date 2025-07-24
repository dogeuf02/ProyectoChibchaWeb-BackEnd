package com.debloopers.chibchaweb.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;


public class ClienteDirectoDTO {

    @Size(max = 50)
    @ClienteDirectoIdClienteValid
    private String idCliente;

    @NotNull
    @Size(max = 50)
    private String nombreCliente;

    @NotNull
    @Size(max = 50)
    private String apellidoCliente;

    @NotNull
    @Size(max = 150)
    private String correoCliente;

    @NotNull
    @Size(max = 50)
    private String contrasenaCliente;

    @NotNull
    @Size(max = 20)
    private String telefono;

    private LocalDate fechaNacimientoCliente;

    private Integer plan;

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

    public String getCorreoCliente() {
        return correoCliente;
    }

    public void setCorreoCliente(final String correoCliente) {
        this.correoCliente = correoCliente;
    }

    public String getContrasenaCliente() {
        return contrasenaCliente;
    }

    public void setContrasenaCliente(final String contrasenaCliente) {
        this.contrasenaCliente = contrasenaCliente;
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

    public Integer getPlan() {
        return plan;
    }

    public void setPlan(final Integer plan) {
        this.plan = plan;
    }

}
