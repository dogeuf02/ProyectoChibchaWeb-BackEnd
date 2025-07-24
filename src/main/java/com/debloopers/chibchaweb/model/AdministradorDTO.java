package com.debloopers.chibchaweb.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AdministradorDTO {

    @Size(max = 20)
    @AdministradorIdAdminValid
    private String idAdmin;

    @NotNull
    @Size(max = 50)
    private String nombreAdmin;

    @NotNull
    @Size(max = 50)
    private String apellidoAdmin;

    @NotNull
    @Size(max = 255)
    private String correoAdmin;

    @NotNull
    @Size(max = 150)
    private String contrasenaAdmin;

    private LocalDate fechaNacimientoAdmin;

}
