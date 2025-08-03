package com.debloopers.chibchaweb.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BancoDTO {

    private Integer idBanco;

    @NotNull
    @Size(max = 100)
    private String nombreBanco;

}