package com.debloopers.chibchaweb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SolicitudDominioRegistroResponseDTO {
    private boolean Exito;
    private String message;
    private Integer idSolicitud;
}