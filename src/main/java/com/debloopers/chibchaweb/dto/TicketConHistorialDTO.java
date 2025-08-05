package com.debloopers.chibchaweb.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TicketConHistorialDTO extends TicketDTO {
    private List<HistorialTicketDTO> historial;
}