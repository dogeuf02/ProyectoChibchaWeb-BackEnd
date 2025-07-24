package com.debloopers.chibchaweb.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;


@Entity
public class TipoDocumentoEmp {

    @Id
    @Column(nullable = false, updatable = false, length = 20)
    private String nombreTipoDoc;

    @OneToMany(mappedBy = "nombreTipoDoc")
    private Set<Distribuidor> nombreTipoDocDistribuidors = new HashSet<>();

    public String getNombreTipoDoc() {
        return nombreTipoDoc;
    }

    public void setNombreTipoDoc(final String nombreTipoDoc) {
        this.nombreTipoDoc = nombreTipoDoc;
    }

    public Set<Distribuidor> getNombreTipoDocDistribuidors() {
        return nombreTipoDocDistribuidors;
    }

    public void setNombreTipoDocDistribuidors(final Set<Distribuidor> nombreTipoDocDistribuidors) {
        this.nombreTipoDocDistribuidors = nombreTipoDocDistribuidors;
    }

}
