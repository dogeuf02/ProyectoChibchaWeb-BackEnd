package com.debloopers.chibchaweb.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Empleado {

    @Id
    @Column(nullable = false, updatable = false, length = 20)
    private String idEmpleado;

    @Column(nullable = false)
    private String correoEmpleado;

    @Column(nullable = false, length = 150)
    private String contrasenaEmpleado;

    @Column(nullable = false, length = 100)
    private String nombreEmpleado;

    @Column(nullable = false, length = 100)
    private String apellidoEmpleado;

    @Column(nullable = false, length = 50)
    private String cargoEmpleado;

    @Column
    private LocalDate fechaNacimientoEmpleado;

}
