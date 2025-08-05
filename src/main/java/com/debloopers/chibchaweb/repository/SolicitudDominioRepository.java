package com.debloopers.chibchaweb.repository;


import com.debloopers.chibchaweb.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface SolicitudDominioRepository extends JpaRepository<SolicitudDominio, Integer> {

    SolicitudDominio findFirstByCliente(ClienteDirecto clienteDirecto);

    SolicitudDominio findFirstByDistribuidor(Distribuidor distribuidor);

    SolicitudDominio findFirstByDominio(Dominio dominio);

    SolicitudDominio findFirstByAdmin(Administrador administrador);

    List<SolicitudDominio> findByCliente_IdCliente(Integer idCliente);

    List<SolicitudDominio> findByDistribuidor_IdDistribuidor(Integer idDistribuidor);

}