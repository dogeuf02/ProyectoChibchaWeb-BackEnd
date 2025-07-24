package com.debloopers.chibchaweb.repos;

import com.debloopers.chibchaweb.domain.Tld;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TldRepository extends JpaRepository<Tld, String> {

    boolean existsByTldIgnoreCase(String tld);

}
