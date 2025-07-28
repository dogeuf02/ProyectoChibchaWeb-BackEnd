package com.debloopers.chibchaweb.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AdministradorConCorreoDTO {
    private Integer idAdmin;
    private String nombreAdmin;
    private String apellidoAdmin;
    private LocalDate fechaNacimientoAdmin;
    private String correo;
    private String estado;
}