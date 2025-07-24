package com.debloopers.chibchaweb.model;

import jakarta.validation.constraints.Size;


public class TipoDocumentoEmpDTO {

    @Size(max = 20)
    @TipoDocumentoEmpNombreTipoDocValid
    private String nombreTipoDoc;

    public String getNombreTipoDoc() {
        return nombreTipoDoc;
    }

    public void setNombreTipoDoc(final String nombreTipoDoc) {
        this.nombreTipoDoc = nombreTipoDoc;
    }

}
