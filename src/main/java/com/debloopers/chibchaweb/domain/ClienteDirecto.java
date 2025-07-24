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
public class ClienteDirecto {

    @Id
    @Column(nullable = false, updatable = false, length = 50)
    private String idCliente;

    @Column(nullable = false, length = 50)
    private String nombreCliente;

    @Column(nullable = false, length = 50)
    private String apellidoCliente;

    @Column(nullable = false)
    private String correoCliente;

    @Column(nullable = false, length = 150)
    private String contrasenaCliente;

    @Column(length = 20)
    private String telefono;

    @Column
    private LocalDate fechaNacimientoCliente;

}
