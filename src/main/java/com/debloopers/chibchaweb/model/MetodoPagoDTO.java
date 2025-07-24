package com.debloopers.chibchaweb.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MetodoPagoDTO {

    private Integer idMetodoPago;

    @NotNull
    @Size(max = 100)
    private String nombreTitular;

    @NotNull
    @Size(max = 20)
    private String marcaTarjeta;

    @NotNull
    @Size(max = 16)
    private String numeroTarjeta;

    @NotNull
    @Size(max = 10)
    private String tipoTarjeta;

    @NotNull
    @Size(max = 3)
    private String cvv;

    @NotNull
    @Size(max = 2)
    private String mesExpiracion;

    @NotNull
    @Size(max = 2)
    private String anoExpiracion;

}
