package com.debloopers.chibchaweb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class TokenVerificacion {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "fecha_expiracion", nullable = false)
    private LocalDateTime fechaExpiracion;

    @Column(name = "creado_en")
    private LocalDateTime creadoEn = LocalDateTime.now();

    @Column(name = "actualizado_en")
    private LocalDateTime actualizadoEn = LocalDateTime.now();

    @Column(nullable = false)
    private Boolean estado = false;
}