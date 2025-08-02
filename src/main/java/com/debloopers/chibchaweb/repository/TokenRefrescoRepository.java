package com.debloopers.chibchaweb.repository;

import com.debloopers.chibchaweb.entity.TokenRefresco;
import com.debloopers.chibchaweb.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRefrescoRepository extends JpaRepository<TokenRefresco, Long> {
    Optional<TokenRefresco> findByToken(String token);
    List<TokenRefresco> findAllByUsuarioAndRevocadoFalse(Usuario usuario);
}