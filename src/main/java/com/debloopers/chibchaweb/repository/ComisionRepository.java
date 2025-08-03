package com.debloopers.chibchaweb.repository;

import com.debloopers.chibchaweb.entity.Comision;
import com.debloopers.chibchaweb.entity.Distribuidor;
import com.debloopers.chibchaweb.entity.MedioPago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComisionRepository extends JpaRepository<Comision, Integer> {

    Comision findFirstByDistribuidor(Distribuidor distribuidor);

    Comision findFirstByMedioPago(MedioPago medioPago);

}