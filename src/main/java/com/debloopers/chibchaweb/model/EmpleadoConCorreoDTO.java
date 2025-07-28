package com.debloopers.chibchaweb.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpleadoConCorreoDTO {

    private Integer idEmpleado;
    private String nombreEmpleado;
    private String apellidoEmpleado;
    private String cargoEmpleado;
    private String correo;
    private String estado;
}