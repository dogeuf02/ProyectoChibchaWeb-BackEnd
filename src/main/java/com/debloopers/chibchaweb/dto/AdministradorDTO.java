package com.debloopers.chibchaweb.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AdministradorDTO {

    private Integer idAdmin;

    @NotNull
    @Size(max = 50)
    private String nombreAdmin;

    @NotNull
    @Size(max = 50)
    private String apellidoAdmin;

    private LocalDate fechaNacimientoAdmin;

}