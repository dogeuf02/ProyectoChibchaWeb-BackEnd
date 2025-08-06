package com.debloopers.chibchaweb.repository;

import com.debloopers.chibchaweb.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SolicitudTrasladoRepository extends JpaRepository<SolicitudTraslado, Integer> {

    SolicitudTraslado findFirstByPertenece(PerteneceDominio perteneceDominio);

    SolicitudTraslado findFirstByCliente(ClienteDirecto clienteDirecto);

    SolicitudTraslado findFirstByDistribuidor(Distribuidor distribuidor);

    SolicitudTraslado findFirstByAdmin(Administrador administrador);

    List<SolicitudTraslado> findByCliente_IdCliente(Integer idCliente);

    List<SolicitudTraslado> findByDistribuidor_IdDistribuidor(Integer idDistribuidor);

}