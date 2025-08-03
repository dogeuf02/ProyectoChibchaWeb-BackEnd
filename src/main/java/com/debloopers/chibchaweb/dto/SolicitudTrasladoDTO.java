package com.debloopers.chibchaweb.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SolicitudTrasladoDTO {

    private Integer idSolicitudTraslado;

    @NotNull
    private LocalDate fechaSolicitudTraslado;

    private LocalDate fechaAprobacionTraslado;

    @NotNull
    @Size(max = 255)
    private String estadoTraslado;

    @NotNull
    private Integer pertenece;

    private Integer cliente;

    private Integer distribuidor;

    private Integer admin;

}