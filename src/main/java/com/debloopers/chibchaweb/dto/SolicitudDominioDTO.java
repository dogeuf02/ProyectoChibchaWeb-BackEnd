package com.debloopers.chibchaweb.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SolicitudDominioDTO {

    private Integer idSolicitud;

    @NotNull
    @Size(max = 255)
    private String estadoSolicitud;

    @NotNull
    private LocalDate fechaSolicitud;

    private LocalDate fechaAprobacion;

    private Integer cliente;

    private Integer distribuidor;

    @NotNull
    private Integer dominio;

    private Integer admin;

}