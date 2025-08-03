package com.debloopers.chibchaweb.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DominioDTO {

    private Integer idDominio;

    @NotNull
    @Size(max = 263)
    private String nombreDominio;

    @Digits(integer = 10, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "49.08")
    private BigDecimal precioDominio;

    @Size(max = 255)
    private String estado;

    @NotNull
    @Size(max = 63)
    private String tld;

}