package com.debloopers.chibchaweb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponseDTO {

    private boolean autenticado;
    private String mensaje;
    private String token;
}