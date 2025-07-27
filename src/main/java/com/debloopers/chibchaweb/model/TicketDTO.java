package com.debloopers.chibchaweb.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TicketDTO {

    @Size(max = 100)
    @TicketIdTicketValid
    private String idTicket;

    @NotNull
    @Size(max = 100)
    private String asunto;

    private String descripcion;

    @Size(max = 255)
    private String prioridad;

    @NotNull
    @Size(max = 255)
    private String estado;

    private Integer distribuidor;

    private Integer cliente;

    private Integer empleado;

    private List<Integer> historialTicketUsuarioEmpleadoes;

}
