package com.debloopers.chibchaweb.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TipoDocumentoEmpDTO {

    private Integer idTipoDocumento;

    @NotNull
    @Size(max = 20)
    private String nombreTipo;

}
