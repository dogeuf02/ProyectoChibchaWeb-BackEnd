package com.debloopers.chibchaweb.repository;


import com.debloopers.chibchaweb.entity.*;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface SolicitudDominioRepository extends JpaRepository<SolicitudDominio, Integer> {

    SolicitudDominio findFirstByCliente(ClienteDirecto clienteDirecto);

    SolicitudDominio findFirstByDistribuidor(Distribuidor distribuidor);

    SolicitudDominio findFirstByDominio(Dominio dominio);

    SolicitudDominio findFirstByAdmin(Administrador administrador);

    boolean existsByCliente_IdClienteAndDominio_IdDominio(Integer clienteId, Integer dominioId);

    boolean existsByDistribuidor_IdDistribuidorAndDominio_IdDominio(Integer distribuidorId, Integer dominioId);

    @Query("SELECT COUNT(s) > 0 FROM SolicitudDominio s " +
            "WHERE s.cliente.idCliente = :clienteId " +
            "AND s.dominio.idDominio = :dominioId " +
            "AND s.estadoSolicitud = 'En Revisión'")
    boolean existsEnRevisionByCliente(@Param("clienteId") Integer clienteId,
                                      @Param("dominioId") Integer dominioId);

    @Query("SELECT COUNT(s) > 0 FROM SolicitudDominio s " +
            "WHERE s.distribuidor.idDistribuidor = :distribuidorId " +
            "AND s.dominio.idDominio = :dominioId " +
            "AND s.estadoSolicitud = 'En Revisión'")
    boolean existsEnRevisionByDistribuidor(@Param("distribuidorId") Integer distribuidorId,
                                           @Param("dominioId") Integer dominioId);

    @Query("SELECT s FROM SolicitudDominio s " +
            "JOIN FETCH s.dominio d " +
            "JOIN FETCH d.tld " +
            "WHERE s.distribuidor.idDistribuidor = :idDistribuidor")
    List<SolicitudDominio> findWithDominioAndTldByDistribuidorId(@Param("idDistribuidor") Integer idDistribuidor);

    @Query("SELECT s FROM SolicitudDominio s " +
            "JOIN FETCH s.dominio d " +
            "JOIN FETCH d.tld " +
            "WHERE s.cliente.idCliente = :idCliente")
    List<SolicitudDominio> findWithDominioAndTldByClienteId(@Param("idCliente") Integer idCliente);

}