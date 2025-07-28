package com.debloopers.chibchaweb.repos;


import com.debloopers.chibchaweb.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SolicitudDominioRepository extends JpaRepository<SolicitudDominio, Integer> {

    SolicitudDominio findFirstByCliente(ClienteDirecto clienteDirecto);

    SolicitudDominio findFirstByDistribuidor(Distribuidor distribuidor);

    SolicitudDominio findFirstByTld(Tld tld);

    SolicitudDominio findFirstByAdmin(Administrador administrador);

}
