package com.debloopers.chibchaweb.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SolicitudDominioActualizarDTO {

    @Size(max = 255)
    private String nombreDominio;

    @Size(max = 255)
    private String estadoDominio;

    @Size(max = 255)
    private String estadoSolicitud;

    private LocalDate fechaSolicitud;

    private LocalDate fechaAprobacion;

    private Integer cliente;

    private Integer distribuidor;

    @Size(max = 63)
    private String tld;

    private Integer admin;
}