package com.debloopers.chibchaweb.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MedioPagoDTO {

    private Integer idMedioPago;

    @NotNull
    @Size(max = 255)
    private String tipoMedioPago;

    @NotNull
    @Size(max = 100)
    private String nombreTitular;

    @Size(max = 20)
    private String numeroTarjetaCuenta;

    @Size(max = 100)
    private String correoPse;

    private OffsetDateTime fechaRegistro;

    private Integer cliente;

    private Integer distribuidor;

    private Integer banco;

}