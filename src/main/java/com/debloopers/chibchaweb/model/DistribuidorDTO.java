package com.debloopers.chibchaweb.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DistribuidorDTO {

    private Integer idDistribuidor;

    @NotNull
    @Size(max = 20)
    private String numeroDocumento;

    @NotNull
    @Size(max = 255)
    private String nombreEmpresa;

    @NotNull
    @Size(max = 255)
    private String correoEmpresa;

    @NotNull
    @Size(max = 150)
    private String contrasenaEmpresa;

    @NotNull
    @Size(max = 255)
    private String direccionEmpresa;

    @NotNull
    private Integer tipoDocumento;

}
