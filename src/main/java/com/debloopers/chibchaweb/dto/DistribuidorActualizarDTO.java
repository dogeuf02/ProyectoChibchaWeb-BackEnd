package com.debloopers.chibchaweb.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DistribuidorActualizarDTO {

    @Size(max = 20)
    private String numeroDocEmpresa;

    @Size(max = 255)
    private String nombreEmpresa;

    @Size(max = 255)
    private String direccionEmpresa;

    @Size(max = 20)
    private String nombreTipoDoc;
}
