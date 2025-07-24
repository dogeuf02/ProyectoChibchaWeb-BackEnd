package com.debloopers.chibchaweb.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EmpleadoDTO {

    @Size(max = 20)
    @EmpleadoIdEmpleadoValid
    private String idEmpleado;

    @NotNull
    @Size(max = 255)
    private String correoEmpleado;

    @NotNull
    @Size(max = 150)
    private String contrasenaEmpleado;

    @NotNull
    @Size(max = 100)
    private String nombreEmpleado;

    @NotNull
    @Size(max = 100)
    private String apellidoEmpleado;

    @NotNull
    @Size(max = 50)
    private String cargoEmpleado;

    private LocalDate fechaNacimientoEmpleado;

}
