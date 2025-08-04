package com.debloopers.chibchaweb.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {

    @NotNull
    private String correo;
    @NotNull
    private String contrasena;
    @NotNull(message = "Captcha token is required")
    private String captchaToken;
}