package com.debloopers.chibchaweb.repository;


import com.debloopers.chibchaweb.entity.Dominio;
import com.debloopers.chibchaweb.entity.Tld;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DominioRepository extends JpaRepository<Dominio, Integer> {

    Dominio findFirstByTld(Tld tld);

}