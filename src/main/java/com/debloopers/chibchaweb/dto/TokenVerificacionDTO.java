package com.debloopers.chibchaweb.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenVerificacionDTO {

    private Long id;

    @NotBlank
    private String token;

    @NotNull
    private Integer idUsuario;

    @NotNull
    private LocalDateTime fechaExpiracion;

    @NotNull
    private boolean estado;

    private LocalDateTime creadoEn;

    private LocalDateTime actualizadoEn;
}