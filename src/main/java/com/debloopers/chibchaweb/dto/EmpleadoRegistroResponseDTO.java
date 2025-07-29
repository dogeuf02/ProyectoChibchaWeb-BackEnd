package com.debloopers.chibchaweb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoRegistroResponseDTO {
    private boolean success;
    private String mensaje;
}
