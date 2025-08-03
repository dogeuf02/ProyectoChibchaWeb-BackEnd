package com.debloopers.chibchaweb.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FacturaDTO {

    private Integer idFactura;

    @NotNull
    private LocalDate fechaFacturacion;

    @NotNull
    @Digits(integer = 10, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "32.08")
    private BigDecimal total;

    @NotNull
    @Size(max = 255)
    private String estadoPago;

    @NotNull
    private Integer planAdquirido;

    @NotNull
    private Integer medioPago;

}