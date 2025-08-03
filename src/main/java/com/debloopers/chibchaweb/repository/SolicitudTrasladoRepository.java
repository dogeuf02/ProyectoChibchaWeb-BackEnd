package com.debloopers.chibchaweb.repository;

import com.debloopers.chibchaweb.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitudTrasladoRepository extends JpaRepository<SolicitudTraslado, Integer> {

    SolicitudTraslado findFirstByPertenece(PerteneceDominio perteneceDominio);

    SolicitudTraslado findFirstByCliente(ClienteDirecto clienteDirecto);

    SolicitudTraslado findFirstByDistribuidor(Distribuidor distribuidor);

    SolicitudTraslado findFirstByAdmin(Administrador administrador);

}