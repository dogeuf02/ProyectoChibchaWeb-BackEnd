package com.debloopers.chibchaweb.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class EmpleadoDTO {

    @Size(max = 10)
    @EmpleadoIdEmpleadoValid
    private String idEmpleado;

    @NotNull
    @Size(max = 50)
    private String nombreEmpleado;

    @NotNull
    @Size(max = 50)
    private String apellidoEmpleado;

    @NotNull
    @Size(max = 100)
    private String cargoEmpleado;

    @NotNull
    @Size(max = 50)
    private String usuarioEmpelado;

    @NotNull
    @Size(max = 50)
    private String contrasenaEmpleado;

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

}
