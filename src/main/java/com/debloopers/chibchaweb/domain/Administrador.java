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
public class Administrador {

    @Id
    @Column(nullable = false, updatable = false, length = 20)
    private String idAdmin;

    @Column(nullable = false, length = 50)
    private String nombreAdmin;

    @Column(nullable = false, length = 50)
    private String apellidoAdmin;

    @Column(nullable = false)
    private String correoAdmin;

    @Column(nullable = false, length = 150)
    private String contrasenaAdmin;

    @Column
    private LocalDate fechaNacimientoAdmin;

}
