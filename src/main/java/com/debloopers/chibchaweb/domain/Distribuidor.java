package com.debloopers.chibchaweb.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Distribuidor {

    @Id
    @Column(nullable = false, updatable = false, length = 20)
    private String numeroDocEmpresa;

    @Column(nullable = false)
    private String nombreEmpresa;

    @Column(nullable = false)
    private String correoEmpresa;

    @Column(nullable = false, length = 150)
    private String contrasenaEmpresa;

    @Column(nullable = false)
    private String direccionEmpresa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nombre_tipo_doc_id", nullable = false)
    private TipoDocumentoEmp nombreTipoDoc;

    @OneToMany(mappedBy = "nombreTipoDoc")
    private Set<SolicitudDomDistribuidor> nombreTipoDocSolicitudDomDistribuidors = new HashSet<>();

    @OneToMany(mappedBy = "nombreTipoDoc")
    private Set<Ticket> nombreTipoDocTickets = new HashSet<>();

    public String getNumeroDocEmpresa() {
        return numeroDocEmpresa;
    }

    public void setNumeroDocEmpresa(final String numeroDocEmpresa) {
        this.numeroDocEmpresa = numeroDocEmpresa;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(final String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getCorreoEmpresa() {
        return correoEmpresa;
    }

    public void setCorreoEmpresa(final String correoEmpresa) {
        this.correoEmpresa = correoEmpresa;
    }

    public String getContrasenaEmpresa() {
        return contrasenaEmpresa;
    }

    public void setContrasenaEmpresa(final String contrasenaEmpresa) {
        this.contrasenaEmpresa = contrasenaEmpresa;
    }

    public String getDireccionEmpresa() {
        return direccionEmpresa;
    }

    public void setDireccionEmpresa(final String direccionEmpresa) {
        this.direccionEmpresa = direccionEmpresa;
    }

    public TipoDocumentoEmp getNombreTipoDoc() {
        return nombreTipoDoc;
    }

    public void setNombreTipoDoc(final TipoDocumentoEmp nombreTipoDoc) {
        this.nombreTipoDoc = nombreTipoDoc;
    }

    public Set<SolicitudDomDistribuidor> getNombreTipoDocSolicitudDomDistribuidors() {
        return nombreTipoDocSolicitudDomDistribuidors;
    }

    public void setNombreTipoDocSolicitudDomDistribuidors(
            final Set<SolicitudDomDistribuidor> nombreTipoDocSolicitudDomDistribuidors) {
        this.nombreTipoDocSolicitudDomDistribuidors = nombreTipoDocSolicitudDomDistribuidors;
    }

    public Set<Ticket> getNombreTipoDocTickets() {
        return nombreTipoDocTickets;
    }

    public void setNombreTipoDocTickets(final Set<Ticket> nombreTipoDocTickets) {
        this.nombreTipoDocTickets = nombreTipoDocTickets;
    }

}
