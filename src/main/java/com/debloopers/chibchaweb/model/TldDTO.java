package com.debloopers.chibchaweb.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TldDTO {

    @Size(max = 63)
    @TldTldValid
    private String tld;
}
