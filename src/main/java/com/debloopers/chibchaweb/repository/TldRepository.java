package com.debloopers.chibchaweb.repository;

import com.debloopers.chibchaweb.entity.Tld;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TldRepository extends JpaRepository<Tld, String> {

    boolean existsByTldIgnoreCase(String tld);

}
