package com.debloopers.chibchaweb.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ClienteDirectoActualizarDTO {

    @Size(max = 50)
    private String nombreCliente;

    @Size(max = 50)
    private String apellidoCliente;

    @Size(max = 20)
    private String telefono;

    private LocalDate fechaNacimientoCliente;

    private Integer plan;
}