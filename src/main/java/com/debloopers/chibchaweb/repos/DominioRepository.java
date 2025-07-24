package com.debloopers.chibchaweb.repos;

import com.debloopers.chibchaweb.domain.Dominio;
import com.debloopers.chibchaweb.domain.Tld;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DominioRepository extends JpaRepository<Dominio, String> {

    Dominio findFirstByTld(Tld tld);

    boolean existsByNombreDominioIgnoreCase(String nombreDominio);

}
