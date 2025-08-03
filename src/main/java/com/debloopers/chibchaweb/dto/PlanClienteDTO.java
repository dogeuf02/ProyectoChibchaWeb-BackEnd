package com.debloopers.chibchaweb.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PlanClienteDTO {

    private Integer idPlanCliente;

    @NotNull
    @Size(max = 150)
    private String nombrePlanCliente;

    @NotNull
    private Integer numeroWebs;

    @NotNull
    private Integer numeroBaseDatos;

    @NotNull
    private Integer almacenamientoNvme;

    @NotNull
    private Integer numeroCuentasCorreo;

    @NotNull
    private Boolean creadorWeb;

    @NotNull
    private Integer numeroCertificadoSslHttps;

    @NotNull
    private Boolean emailMarketing;

}