package com.debloopers.chibchaweb.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AdministradorRegistroRequestDTO {

    @NotNull
    @Size(max = 50)
    private String nombreAdmin;

    @NotNull
    @Size(max = 50)
    private String apellidoAdmin;

    private LocalDate fechaNacimientoAdmin;

    @NotNull
    @Email
    private String correoAdmin;

    @NotNull
    @Size(min = 6)
    private String contrasenaAdmin;
}
