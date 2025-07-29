package com.debloopers.chibchaweb.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DistribuidorConCorreoDTO {

    private Integer idDistribuidor;
    private String numeroDocEmpresa;
    private String nombreEmpresa;
    private String direccionEmpresa;
    private String nombreTipoDoc;
    private String correo;
    private String estado;
}
