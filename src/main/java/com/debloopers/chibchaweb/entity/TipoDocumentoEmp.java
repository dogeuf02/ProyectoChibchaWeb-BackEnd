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
    
    public TipoDocumentoEmp(){}
    public TipoDocumentoEmp(String nombre){
        this.nombreTipoDoc = nombre;
    }
    @Id
    @Column(nullable = false, updatable = false, length = 20)
    private String nombreTipoDoc;

    @OneToMany(mappedBy = "nombreTipoDoc")
    private Set<Distribuidor> nombreTipoDocDistribuidors = new HashSet<>();

    public void save(TipoDocumentoEmp tipoDocumentoEmp) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }
}
