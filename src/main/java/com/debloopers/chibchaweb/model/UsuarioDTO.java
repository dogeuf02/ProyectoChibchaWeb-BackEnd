package com.debloopers.chibchaweb.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Data transfer object para Usuario")
public class UsuarioDTO {

    @Schema(description = "ID generado", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer idUsuario;

    @NotNull
    @Email
    @Size(max = 150)
    private String correoUsuario;

    @NotNull
    @Size(min = 6, max = 150)
    private String contrasena;

    @NotNull
    private String rolUsuario;

    @Schema(description = "Si el rol es CLIENTE, aquí va el ID_CLIENTE", example = "42", required = false)
    private String idCliente;

    @Schema(description = "Si el rol es ADMINISTRADOR, aquí va el ID_ADMIN", required = false)
    private String idAdmin;

    @Schema(description = "Si el rol es EMPLEADO, aquí va el ID_EMPLEADO", required = false)
    private String idEmpleado;

    @Schema(description = "Si el rol es REGISTRADOR, aquí va el ID_REGISTRADOR", required = false)
    private String idRegistrador;

    // Getters / Setters

    public Integer getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }
    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getRolUsuario() {
        return rolUsuario;
    }

    public void setRolUsuario(String rolUsuario) {
        this.rolUsuario = rolUsuario;
    }

    public String getIdCliente() {
        return idCliente;
    }
    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getIdAdmin() {
        return idAdmin;
    }
    public void setIdAdmin(String idAdmin) {
        this.idAdmin = idAdmin;
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }
    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getIdRegistrador() {
        return idRegistrador;
    }
    public void setIdRegistrador(String idRegistrador) {
        this.idRegistrador = idRegistrador;
    }
}
