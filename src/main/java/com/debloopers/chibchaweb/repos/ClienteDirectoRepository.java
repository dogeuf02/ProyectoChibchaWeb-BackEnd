package com.debloopers.chibchaweb.repos;

import com.debloopers.chibchaweb.domain.ClienteDirecto;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClienteDirectoRepository extends JpaRepository<ClienteDirecto, String> {

    boolean existsByIdClienteIgnoreCase(String idCliente);

}
