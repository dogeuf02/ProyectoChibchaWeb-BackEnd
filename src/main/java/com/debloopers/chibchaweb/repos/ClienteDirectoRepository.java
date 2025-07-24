package com.debloopers.chibchaweb.repos;

import com.debloopers.chibchaweb.domain.ClienteDirecto;
import com.debloopers.chibchaweb.domain.Plan;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClienteDirectoRepository extends JpaRepository<ClienteDirecto, String> {

    ClienteDirecto findFirstByPlan(Plan plan);

    boolean existsByIdClienteIgnoreCase(String idCliente);

}
