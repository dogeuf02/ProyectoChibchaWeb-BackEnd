package com.debloopers.chibchaweb.repository;


import com.debloopers.chibchaweb.entity.Dominio;
import com.debloopers.chibchaweb.entity.Tld;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DominioRepository extends JpaRepository<Dominio, Integer> {

    Dominio findFirstByTld(Tld tld);

    Optional<Dominio> findByNombreDominioAndTld_Tld(String nombreDominio, String tldId);

    boolean existsByNombreDominioAndTld_Tld(String nombreDominio, String tldId);

}