package com.debloopers.chibchaweb.repository;

import com.debloopers.chibchaweb.entity.TokenListaNegra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenListaNegraRepository extends JpaRepository<TokenListaNegra, Long> {
    Optional<TokenListaNegra> findByToken(String token);
    boolean existsByToken(String token);
}

