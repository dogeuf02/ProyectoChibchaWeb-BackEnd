package com.debloopers.chibchaweb.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RegistradorDTO {

    private Integer idRegistrador;

    @NotNull
    @Size(max = 150)
    private String nombreRegistrador;

    @NotNull
    @Size(max = 150)
    private String correoRegistrador;

}