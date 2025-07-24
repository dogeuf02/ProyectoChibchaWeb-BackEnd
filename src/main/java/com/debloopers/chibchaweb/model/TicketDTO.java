package com.debloopers.chibchaweb.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TicketDTO {

    private Integer idTicket;

    @NotNull
    @Size(max = 255)
    private String asunto;

    private String descripcion;

    @Size(max = 20)
    private String prioridad;

    @Size(max = 20)
    private String estado;

}
