package com.debloopers.chibchaweb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AdministradorRegistroResponseDTO {
    private boolean creado;
    private String mensaje;
}