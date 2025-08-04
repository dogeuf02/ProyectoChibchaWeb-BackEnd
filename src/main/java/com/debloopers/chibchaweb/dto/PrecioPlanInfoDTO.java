package com.debloopers.chibchaweb.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PrecioPlanInfoDTO {

    private Long id;
    private BigDecimal precio;

    private PlanClienteDTO planCliente;

    private PlanPagoDTO planPago;
}