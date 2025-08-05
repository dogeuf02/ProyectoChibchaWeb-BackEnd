package com.debloopers.chibchaweb.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecuperarContrasenaDTO {
    private String email;
    private String captchaToken;
}
