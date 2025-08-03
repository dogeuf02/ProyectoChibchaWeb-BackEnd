package com.debloopers.chibchaweb.repository;

import com.debloopers.chibchaweb.entity.CategoriaDistribuidor;
import com.debloopers.chibchaweb.entity.Distribuidor;
import com.debloopers.chibchaweb.entity.TipoDocumentoEmp;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DistribuidorRepository extends JpaRepository<Distribuidor, Integer> {

    Distribuidor findFirstByNombreTipoDoc(TipoDocumentoEmp tipoDocumentoEmp);

    Distribuidor findFirstByCategoria(CategoriaDistribuidor categoriaDistribuidor);

}