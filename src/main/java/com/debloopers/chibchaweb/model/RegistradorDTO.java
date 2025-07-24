package com.debloopers.chibchaweb.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RegistradorDTO {

    @Size(max = 20)
    @RegistradorIdRegistradorValid
    private String idRegistrador;

    @NotNull
    @Size(max = 100)
    private String nombreRegistrador;

    @NotNull
    @Size(max = 255)
    private String correoRegistrador;

}
