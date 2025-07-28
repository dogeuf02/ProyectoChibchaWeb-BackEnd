package com.debloopers.chibchaweb.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class ClienteDirectoConCorreoDTO {
    private Integer idCliente;
    private String nombreCliente;
    private String apellidoCliente;
    private String telefono;
    private LocalDate fechaNacimientoCliente;
    private Integer plan;
    private String correo;
    private String estado;
}
