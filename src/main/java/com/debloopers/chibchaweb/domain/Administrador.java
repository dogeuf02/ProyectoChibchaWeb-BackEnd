package com.debloopers.chibchaweb.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Administrador {

    @Id
    @Column(nullable = false, updatable = false, length = 20)
    private String idAdmin;

    @Column(nullable = false, length = 50)
    private String nombreAdmin;

    @Column(nullable = false, length = 50)
    private String apellidoAdmin;

    @Column(nullable = false)
    private LocalDate fechaNacimientoAdmin;

    @OneToMany(mappedBy = "admin")
    private Set<SolicitudDomCd> adminSolicitudDomCds = new HashSet<>();

    @OneToMany(mappedBy = "admin")
    private Set<SolicitudDomDistribuidor> adminSolicitudDomDistribuidors = new HashSet<>();

    @OneToMany(mappedBy = "admin")
    private Set<Usuario> adminUsuarios = new HashSet<>();

    public String getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(final String idAdmin) {
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

    public Set<SolicitudDomCd> getAdminSolicitudDomCds() {
        return adminSolicitudDomCds;
    }

    public void setAdminSolicitudDomCds(final Set<SolicitudDomCd> adminSolicitudDomCds) {
        this.adminSolicitudDomCds = adminSolicitudDomCds;
    }

    public Set<SolicitudDomDistribuidor> getAdminSolicitudDomDistribuidors() {
        return adminSolicitudDomDistribuidors;
    }

    public void setAdminSolicitudDomDistribuidors(
            final Set<SolicitudDomDistribuidor> adminSolicitudDomDistribuidors) {
        this.adminSolicitudDomDistribuidors = adminSolicitudDomDistribuidors;
    }

    public Set<Usuario> getAdminUsuarios() {
        return adminUsuarios;
    }

    public void setAdminUsuarios(final Set<Usuario> adminUsuarios) {
        this.adminUsuarios = adminUsuarios;
    }

}
