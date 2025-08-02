package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.entity.TokenRefresco;
import com.debloopers.chibchaweb.entity.Usuario;
import com.debloopers.chibchaweb.repository.TokenRefrescoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TokenRefrescoService {

    @Autowired
    private TokenRefrescoRepository tokenRefrescoRepository;

    public TokenRefresco crearToken(Usuario usuario, String token, LocalDateTime expiracion) {
        TokenRefresco nuevo = new TokenRefresco();
        nuevo.setUsuario(usuario);
        nuevo.setToken(token);
        nuevo.setFechaExpiracion(expiracion);
        return tokenRefrescoRepository.save(nuevo);
    }

    public void revocarTokensActivos(Usuario usuario) {
        List<TokenRefresco> tokens = tokenRefrescoRepository.findAllByUsuarioAndRevocadoFalse(usuario);
        tokens.forEach(t -> {
            t.setRevocado(true);
            t.setActualizadoEn(LocalDateTime.now());
        });
        tokenRefrescoRepository.saveAll(tokens);
    }

    public Optional<TokenRefresco> obtenerPorToken(String token) {
        return tokenRefrescoRepository.findByToken(token);
    }
}