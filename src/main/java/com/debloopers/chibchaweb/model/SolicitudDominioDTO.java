package com.debloopers.chibchaweb.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SolicitudDominioDTO {

    private Integer idSolicitud;

    @NotNull
    @Size(max = 255)
    private String nombreDominio;

    @Size(max = 255)
    private String estadoDominio;

    @NotNull
    @Size(max = 255)
    private String estadoSolicitud;

    @NotNull
    private LocalDate fechaSolicitud;

    private LocalDate fechaAprobacion;

    private Integer cliente;

    private Integer distribuidor;

    @NotNull
    @Size(max = 63)
    private String tld;

    private Integer admin;
}
