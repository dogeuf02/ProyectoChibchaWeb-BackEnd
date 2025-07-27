package com.debloopers.chibchaweb.repos;

import com.debloopers.chibchaweb.domain.Administrador;
import com.debloopers.chibchaweb.domain.Distribuidor;
import com.debloopers.chibchaweb.domain.Dominio;
import com.debloopers.chibchaweb.domain.Registrador;
import com.debloopers.chibchaweb.domain.SolicitudDomDistribuidor;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SolicitudDomDistribuidorRepository extends JpaRepository<SolicitudDomDistribuidor, String> {

    SolicitudDomDistribuidor findFirstByDistribuidor(Distribuidor distribuidor);

    SolicitudDomDistribuidor findFirstByNombreDominio(Dominio dominio);

    SolicitudDomDistribuidor findFirstByAdmin(Administrador administrador);

    SolicitudDomDistribuidor findFirstByRegistrador(Registrador registrador);

    boolean existsByTldIgnoreCase(String tld);

}
