package com.debloopers.chibchaweb.dto;

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
    private String numeroDocEmpresa;

    @NotNull
    @Size(max = 255)
    private String nombreEmpresa;

    @NotNull
    @Size(max = 255)
    private String direccionEmpresa;

    @NotNull
    @Size(max = 20)
    private String nombreTipoDoc;

}
