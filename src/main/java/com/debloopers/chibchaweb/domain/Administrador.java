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

    @Column(nullable = false, length = 150)
    private String correoAdmin;

    @Column(nullable = false, length = 50)
    private String contrasenaAdmin;

    @Column(nullable = false)
    private LocalDate fechaNacimientoAdmin;

    @OneToMany(mappedBy = "admin")
    private Set<SolicitudDomCd> adminSolicitudDomCds = new HashSet<>();

    @OneToMany(mappedBy = "admin")
    private Set<SolicitudDomDistribuidor> adminSolicitudDomDistribuidors = new HashSet<>();

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

    public String getCorreoAdmin() {
        return correoAdmin;
    }

    public void setCorreoAdmin(final String correoAdmin) {
        this.correoAdmin = correoAdmin;
    }

    public String getContrasenaAdmin() {
        return contrasenaAdmin;
    }

    public void setContrasenaAdmin(final String contrasenaAdmin) {
        this.contrasenaAdmin = contrasenaAdmin;
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

}
