package com.debloopers.chibchaweb.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Administrador {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Integer idAdmin;

    @Column(nullable = false, length = 50)
    private String nombreAdmin;

    @Column(nullable = false, length = 50)
    private String apellidoAdmin;

    @Column
    private LocalDate fechaNacimientoAdmin;

    @OneToMany(mappedBy = "admin")
    private Set<Usuario> adminUsuarios = new HashSet<>();

    @OneToMany(mappedBy = "admin")
    private Set<SolicitudDominio> adminSolicitudDominios = new HashSet<>();

    public Integer getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(final Integer idAdmin) {
        this.idAdmin = idAdmin;
    }

    public String getNombreAdmin() {
        return nombreAdmin;
    }

    public void setNombreAdmin(final String nombreAdmin) {
        this.nombreAdmin = nombreAdmin;
    }

    public String getApellidoAdmin() {
        return apellidoAdmin;
    }

    public void setApellidoAdmin(final String apellidoAdmin) {
        this.apellidoAdmin = apellidoAdmin;
    }

    public LocalDate getFechaNacimientoAdmin() {
        return fechaNacimientoAdmin;
    }

    public void setFechaNacimientoAdmin(final LocalDate fechaNacimientoAdmin) {
        this.fechaNacimientoAdmin = fechaNacimientoAdmin;
    }

    public Set<Usuario> getAdminUsuarios() {
        return adminUsuarios;
    }

    public void setAdminUsuarios(final Set<Usuario> adminUsuarios) {
        this.adminUsuarios = adminUsuarios;
    }

    public Set<SolicitudDominio> getAdminSolicitudDominios() {
        return adminSolicitudDominios;
    }

    public void setAdminSolicitudDominios(final Set<SolicitudDominio> adminSolicitudDominios) {
        this.adminSolicitudDominios = adminSolicitudDominios;
    }

}