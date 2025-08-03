package com.debloopers.chibchaweb.repository;

import com.debloopers.chibchaweb.entity.Factura;
import com.debloopers.chibchaweb.entity.MedioPago;
import com.debloopers.chibchaweb.entity.PlanAdquirido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaRepository extends JpaRepository<Factura, Integer> {

    Factura findFirstByPlanAdquirido(PlanAdquirido planAdquirido);

    Factura findFirstByMedioPago(MedioPago medioPago);

}