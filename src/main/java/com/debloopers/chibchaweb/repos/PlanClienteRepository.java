package com.debloopers.chibchaweb.repos;

import com.debloopers.chibchaweb.domain.PlanCliente;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PlanClienteRepository extends JpaRepository<PlanCliente, String> {

    boolean existsByIdPcIgnoreCase(String idPc);

}
