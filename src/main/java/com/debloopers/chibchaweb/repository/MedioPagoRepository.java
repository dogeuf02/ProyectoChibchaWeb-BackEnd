package com.debloopers.chibchaweb.repository;

import com.debloopers.chibchaweb.entity.Banco;
import com.debloopers.chibchaweb.entity.ClienteDirecto;
import com.debloopers.chibchaweb.entity.Distribuidor;
import com.debloopers.chibchaweb.entity.MedioPago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedioPagoRepository extends JpaRepository<MedioPago, Integer> {

    MedioPago findFirstByCliente(ClienteDirecto clienteDirecto);

    MedioPago findFirstByDistribuidor(Distribuidor distribuidor);

    MedioPago findFirstByBanco(Banco banco);

}
