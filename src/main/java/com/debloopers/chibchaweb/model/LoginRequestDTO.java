package com.debloopers.chibchaweb.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {
    private String correo;
    private String contrasena;
}