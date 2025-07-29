package com.debloopers.chibchaweb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ClienteDirectoRegistroResponseDTO {
    private boolean exito;
    private String mensaje;
}