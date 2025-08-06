package com.debloopers.chibchaweb.repository;

import com.debloopers.chibchaweb.entity.ClienteDirecto;
import com.debloopers.chibchaweb.entity.Distribuidor;
import com.debloopers.chibchaweb.entity.Dominio;
import com.debloopers.chibchaweb.entity.PerteneceDominio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PerteneceDominioRepository extends JpaRepository<PerteneceDominio, Integer> {

    PerteneceDominio findFirstByCliente(ClienteDirecto clienteDirecto);

    PerteneceDominio findFirstByDistribuidor(Distribuidor distribuidor);

    PerteneceDominio findFirstByDominio(Dominio dominio);

    List<PerteneceDominio> findByCliente_IdCliente(Integer idCliente);

    List<PerteneceDominio> findByDistribuidor_IdDistribuidor(Integer idDistribuidor);

    long countByDistribuidor_IdDistribuidor(Integer idDistribuidor);

}