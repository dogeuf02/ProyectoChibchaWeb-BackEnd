package com.debloopers.chibchaweb.repository;

import com.debloopers.chibchaweb.entity.ClienteDirecto;
import com.debloopers.chibchaweb.entity.PlanAdquirido;
import com.debloopers.chibchaweb.entity.PlanCliente;
import com.debloopers.chibchaweb.entity.PlanPago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanAdquiridoRepository extends JpaRepository<PlanAdquirido, Integer> {

    PlanAdquirido findFirstByCliente(ClienteDirecto clienteDirecto);

    PlanAdquirido findFirstByPlanCliente(PlanCliente planCliente);

    PlanAdquirido findFirstByPlanPago(PlanPago planPago);

}