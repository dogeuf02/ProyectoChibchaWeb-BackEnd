package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.entity.TokenListaNegra;
import com.debloopers.chibchaweb.entity.Usuario;
import com.debloopers.chibchaweb.repository.TokenListaNegraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TokenListaNegraService {

    @Autowired
    private TokenListaNegraRepository tokenListaNegraRepository;

    public TokenListaNegra bloquearToken(String token, LocalDateTime expiracion, Usuario usuario) {
        TokenListaNegra bloqueado = new TokenListaNegra();
        bloqueado.setToken(token);
        bloqueado.setFechaExpiracion(expiracion);
        bloqueado.setUsuario(usuario);
        return tokenListaNegraRepository.save(bloqueado);
    }

    public boolean estaBloqueado(String token) {
        return tokenListaNegraRepository.existsByToken(token);
    }
}