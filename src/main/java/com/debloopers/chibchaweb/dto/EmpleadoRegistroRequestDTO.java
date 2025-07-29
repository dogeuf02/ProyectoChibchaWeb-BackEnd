package com.debloopers.chibchaweb.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpleadoRegistroRequestDTO {

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
    @Email
    @Size(max = 255)
    private String correo;

    @NotNull
    @Size(min = 6, max = 255)
    private String contrasena;
}