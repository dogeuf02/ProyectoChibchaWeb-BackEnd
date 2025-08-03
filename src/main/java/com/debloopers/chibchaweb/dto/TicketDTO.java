package com.debloopers.chibchaweb.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TicketDTO {

    @Size(max = 100)
    private String idTicket;

    @NotNull
    @Size(max = 100)
    private String asunto;

    private String descripcion;

    @NotNull
    @Size(max = 255)
    private String nivelComplejidad;

    @NotNull
    @Size(max = 255)
    private String estado;

    private OffsetDateTime fechaCreacion;

    private OffsetDateTime fechaCierre;

    private Integer distribuidor;

    private Integer cliente;

}