package com.debloopers.chibchaweb.repository;

import com.debloopers.chibchaweb.entity.ClienteDirecto;
import com.debloopers.chibchaweb.entity.PlanCliente;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClienteDirectoRepository extends JpaRepository<ClienteDirecto, Integer> {

    ClienteDirecto findFirstByPlanCliente(PlanCliente planCliente);

}