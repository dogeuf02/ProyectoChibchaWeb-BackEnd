package com.debloopers.chibchaweb.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class ClienteDirectoRegistroDTO {

    @NotNull
    @Email
    @Size(max = 150)
    private String correoCliente;

    @NotNull
    @Size(min = 6, max = 150)  // al menos 6 caracteres, como ejemplo
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
