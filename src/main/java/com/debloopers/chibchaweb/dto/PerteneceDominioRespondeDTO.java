package com.debloopers.chibchaweb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PerteneceDominioRespondeDTO {
    private long total;
    private List<PerteneceDominioDTO> registros;
}
