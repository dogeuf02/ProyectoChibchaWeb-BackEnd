package com.debloopers.chibchaweb.repository;

import com.debloopers.chibchaweb.entity.TokenVerificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenVerificacionRepository extends JpaRepository<TokenVerificacion, Long> {
    Optional<TokenVerificacion> findByToken(String token);
}