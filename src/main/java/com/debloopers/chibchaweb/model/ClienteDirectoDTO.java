package com.debloopers.chibchaweb.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class ClienteDirectoDTO {

     @Size(max = 50)
     @ClienteDirectoIdClienteValid
     private String idCliente;

    @NotNull
    @Email
    @Size(max = 150)
    private String correoCliente;

    @NotNull
    @Size(min = 6, max = 150)  // al menos 6 caracteres, como ejemplo
    private String contrasenaCliente;

    @NotNull
    @Size(max = 50)
    private String nombreCliente;

    @NotNull
    @Size(max = 50)
    private String apellidoCliente;

    @NotNull
    @Size(max = 20)
    private String telefono;

    private LocalDate fechaNacimientoCliente;

    private Integer plan;

    // Getters & Setters

    public String getCorreoCliente() {
        return correoCliente;
    }

    public void setCorreoCliente(String correoCliente) {
        this.correoCliente = correoCliente;
    }

    public String getContrasenaCliente() {
        return contrasenaCliente;
    }

    public void setContrasenaCliente(String contrasenaCliente) {
        this.contrasenaCliente = contrasenaCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getApellidoCliente() {
        return apellidoCliente;
    }

    public void setApellidoCliente(String apellidoCliente) {
        this.apellidoCliente = apellidoCliente;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaNacimientoCliente() {
        return fechaNacimientoCliente;
    }

    public void setFechaNacimientoCliente(LocalDate fechaNacimientoCliente) {
        this.fechaNacimientoCliente = fechaNacimientoCliente;
    }

    public Integer getPlan() {
        return plan;
    }

    public void setPlan(Integer plan) {
        this.plan = plan;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }
}