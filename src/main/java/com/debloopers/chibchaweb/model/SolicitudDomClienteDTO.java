package com.debloopers.chibchaweb.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SolicitudDomClienteDTO {

    @Size(max = 63)
    @SolicitudDomClienteTldValid
    private String tld;

    @NotNull
    private LocalDate fechaSolicitud;

    @NotNull
    @Size(max = 255)
    private String estadoSolicitud;

    private LocalDate fechaRevision;

    private LocalDate fechaEnvio;

    @NotNull
    private Integer cliente;

    @NotNull
    @Size(max = 253)
    private String nombreDominio;

    private Integer admin;

    private Integer registrador;

}
