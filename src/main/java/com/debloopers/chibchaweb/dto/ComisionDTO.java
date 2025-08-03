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
public class ComisionDTO {

    private Integer idComision;

    @NotNull
    @Digits(integer = 10, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "21.08")
    private BigDecimal valorComision;

    @NotNull
    private LocalDate fechaPago;

    @NotNull
    @Size(max = 255)
    private String estadoComision;

    @NotNull
    private Integer distribuidor;

    @NotNull
    private Integer medioPago;

}