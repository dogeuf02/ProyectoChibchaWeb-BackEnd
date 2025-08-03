package com.debloopers.chibchaweb.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PerteneceDominioDTO {

    private Integer idPertenece;

    private Integer cliente;

    private Integer distribuidor;

    @NotNull
    private Integer dominio;

}