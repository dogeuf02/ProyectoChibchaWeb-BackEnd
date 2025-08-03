package com.debloopers.chibchaweb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SolicitudDominioRegistroResponseDTO {
    private boolean exito;
    private String message;
    private Integer idSolicitud;
}