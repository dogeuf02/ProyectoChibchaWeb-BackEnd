package com.debloopers.chibchaweb.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UsuarioActualizarDTO {

    @Size(max = 150)
    private String contrasena;

    @Size(max = 255)
    private String estado;
}
