package com.debloopers.chibchaweb.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;


public class AdministradorDTO {

    @Size(max = 20)
    @AdministradorIdAdminValid
    private String idAdmin;

    @NotNull
    @Size(max = 50)
    private String nombreAdmin;

    @NotNull
    @Size(max = 50)
    private String apellidoAdmin;

    @NotNull
    @Size(max = 150)
    private String correoAdmin;

    @NotNull
    @Size(max = 50)
    private String contrasenaAdmin;

    @NotNull
    private LocalDate fechaNacimientoAdmin;

    public String getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(final String idAdmin) {
        this.idAdmin = idAdmin;
    }

    public String getNombreAdmin() {
        return nombreAdmin;
    }

    public void setNombreAdmin(final String nombreAdmin) {
        this.nombreAdmin = nombreAdmin;
    }

    public String getApellidoAdmin() {
        return apellidoAdmin;
    }

    public void setApellidoAdmin(final String apellidoAdmin) {
        this.apellidoAdmin = apellidoAdmin;
    }

    public String getCorreoAdmin() {
        return correoAdmin;
    }

    public void setCorreoAdmin(final String correoAdmin) {
        this.correoAdmin = correoAdmin;
    }

    public String getContrasenaAdmin() {
        return contrasenaAdmin;
    }

    public void setContrasenaAdmin(final String contrasenaAdmin) {
        this.contrasenaAdmin = contrasenaAdmin;
    }

    public LocalDate getFechaNacimientoAdmin() {
        return fechaNacimientoAdmin;
    }

    public void setFechaNacimientoAdmin(final LocalDate fechaNacimientoAdmin) {
        this.fechaNacimientoAdmin = fechaNacimientoAdmin;
    }

}
