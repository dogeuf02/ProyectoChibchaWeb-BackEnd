package com.debloopers.chibchaweb.repository;

import com.debloopers.chibchaweb.entity.TipoDocumentoEmp;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TipoDocumentoEmpRepository extends JpaRepository<TipoDocumentoEmp, String> {

    boolean existsByNombreTipoDocIgnoreCase(String nombreTipoDoc);
    TipoDocumentoEmp findByNombreTipoDoc(String nombreTipoDoc);

}
