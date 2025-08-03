package com.debloopers.chibchaweb.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EmpleadoDTO {

    private Integer idEmpleado;

    @NotNull
    @Size(max = 50)
    private String nombreEmpleado;

    @NotNull
    @Size(max = 50)
    private String apellidoEmpleado;

    @NotNull
    @Size(max = 255)
    private String cargoEmpleado;

}