package com.debloopers.chibchaweb.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AdministradorActualizarDTO {

    @Size(max = 50)
    private String nombreAdmin;

    @Size(max = 50)
    private String apellidoAdmin;

    private LocalDate fechaNacimientoAdmin;
}