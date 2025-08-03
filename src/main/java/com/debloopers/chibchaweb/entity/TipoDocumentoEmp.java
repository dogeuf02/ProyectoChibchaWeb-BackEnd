package com.debloopers.chibchaweb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class TipoDocumentoEmp {

    @Id
    @Column(nullable = false, updatable = false, length = 20)
    private String nombreTipoDoc;

    @OneToMany(mappedBy = "nombreTipoDoc")
    private Set<Distribuidor> nombreTipoDocDistribuidors = new HashSet<>();

}