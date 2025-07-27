package com.debloopers.chibchaweb.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DistribuidorRegistroRequestDTO {

    @NotNull
    @Email
    @Size(max = 150)
    private String correoDistrbuidor;

    @NotNull
    @Size(min = 6, max = 150)
    private String contrasenaDistribuidor;

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
