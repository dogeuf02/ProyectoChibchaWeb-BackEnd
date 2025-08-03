package com.debloopers.chibchaweb.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PlanAdquiridoDTO {

    private Integer idPlanAdquirido;

    @NotNull
    @Size(max = 255)
    private String estadoPlan;

    @NotNull
    private LocalDate fechaCompra;

    @NotNull
    private LocalDate fechaExpiracion;

    private OffsetDateTime fechaActualizacion;

    @Digits(integer = 10, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "67.08")
    private BigDecimal precioPlanAdquirido;

    @NotNull
    private Integer cliente;

    @NotNull
    private Integer planCliente;

    @NotNull
    private Integer planPago;

}