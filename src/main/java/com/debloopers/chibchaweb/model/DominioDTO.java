package com.debloopers.chibchaweb.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DominioDTO {

    @Size(max = 253)
    @DominioNombreDominioValid
    private String nombreDominio;

    @Size(max = 63)
    private String tld;

}
