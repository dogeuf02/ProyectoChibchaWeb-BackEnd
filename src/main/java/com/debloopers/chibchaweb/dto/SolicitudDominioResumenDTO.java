package com.debloopers.chibchaweb.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class SolicitudDominioResumenDTO {
    private Integer idSolicitud;
    private String estadoSolicitud;
    private LocalDate fechaSolicitud;
    private LocalDate fechaAprobacion;
    private String nombreDominio;
    private BigDecimal precioDominio;
    private String tld;
    private BigDecimal precioTld;
}