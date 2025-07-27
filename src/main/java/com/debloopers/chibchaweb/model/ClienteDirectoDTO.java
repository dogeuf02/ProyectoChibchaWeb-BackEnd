package com.debloopers.chibchaweb.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ClienteDirectoDTO {

    private Integer idCliente;

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

    private Integer plan;

}
