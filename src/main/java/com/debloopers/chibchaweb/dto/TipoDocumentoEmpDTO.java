package com.debloopers.chibchaweb.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TipoDocumentoEmpDTO {

    @Size(max = 20)
    @TipoDocumentoEmpNombreTipoDocValid
    private String nombreTipoDoc;

}
