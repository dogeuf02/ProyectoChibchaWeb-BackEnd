package com.debloopers.chibchaweb.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UsuarioDTO {

    private Integer idUsuario;

    @NotNull
    @Size(max = 150)
    private String correoUsuario;

    @NotNull
    @Size(max = 150)
    private String contrasena;

    @NotNull
    @Size(max = 255)
    private String rol;

    @NotNull
    @Size(max = 255)
    private String estado;

    private Integer cliente;

    private Integer admin;

    private Integer empleado;

    private Integer distribuidor;

}