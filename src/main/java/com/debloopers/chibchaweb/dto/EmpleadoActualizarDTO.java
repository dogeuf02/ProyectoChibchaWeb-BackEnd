package com.debloopers.chibchaweb.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpleadoActualizarDTO {

    @Size(max = 50)
    private String nombreEmpleado;

    @Size(max = 50)
    private String apellidoEmpleado;

    @Size(max = 255)
    private String cargoEmpleado;
}
