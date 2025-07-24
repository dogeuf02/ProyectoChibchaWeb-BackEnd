package com.debloopers.chibchaweb.repos;

import com.debloopers.chibchaweb.domain.TipoDocumentoEmp;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TipoDocumentoEmpRepository extends JpaRepository<TipoDocumentoEmp, String> {

    boolean existsByNombreTipoDocIgnoreCase(String nombreTipoDoc);

}
