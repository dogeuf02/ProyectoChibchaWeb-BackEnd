package com.debloopers.chibchaweb.repos;

import com.debloopers.chibchaweb.domain.Distribuidor;
import com.debloopers.chibchaweb.domain.TipoDocumentoEmp;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DistribuidorRepository extends JpaRepository<Distribuidor, Integer> {

    Distribuidor findFirstByTipoDocumento(TipoDocumentoEmp tipoDocumentoEmp);

}
