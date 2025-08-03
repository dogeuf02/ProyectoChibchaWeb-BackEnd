package com.debloopers.chibchaweb.repository;

import com.debloopers.chibchaweb.entity.PlanCliente;
import com.debloopers.chibchaweb.entity.PlanPago;
import com.debloopers.chibchaweb.entity.PrecioPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrecioPlanRepository extends JpaRepository<PrecioPlan, Long> {

    PrecioPlan findFirstByPlanCliente(PlanCliente planCliente);

    PrecioPlan findFirstByPlanPago(PlanPago planPago);

}