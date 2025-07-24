package com.debloopers.chibchaweb.repos;

import com.debloopers.chibchaweb.domain.Registrador;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RegistradorRepository extends JpaRepository<Registrador, String> {

    boolean existsByIdRegistradorIgnoreCase(String idRegistrador);

}
