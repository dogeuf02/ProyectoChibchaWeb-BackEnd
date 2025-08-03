package com.debloopers.chibchaweb.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class HistorialTicketDTO {

    private Integer idHistorialTicket;

    @NotNull
    @Size(max = 255)
    private String accionTicket;

    private String comentarios;

    @NotNull
    private OffsetDateTime fechaAccion;

    @NotNull
    @Size(max = 100)
    private String ticket;

    private Integer empleadoRealizador;

    private Integer empleadoReceptor;

}