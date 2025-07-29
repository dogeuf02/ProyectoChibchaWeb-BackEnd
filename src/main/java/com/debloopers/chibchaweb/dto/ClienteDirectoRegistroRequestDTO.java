package com.debloopers.chibchaweb.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class ClienteDirectoRegistroRequestDTO {

    @NotNull
    @Email
    @Size(max = 150)
    private String correoCliente;

    @NotNull
    @Size(min = 6, max = 150)
    private String contrasenaCliente;

    @NotNull
    @Size(max = 50)
    private String nombreCliente;

    @NotNull
    @Size(max = 50)
    private String apellidoCliente;

    @NotNull
    @Size(max = 20)
    private String telefono;

    private LocalDate fechaNacimientoCliente;
}
