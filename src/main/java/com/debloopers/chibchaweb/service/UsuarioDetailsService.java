package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.entity.Usuario;
import com.debloopers.chibchaweb.repository.UsuarioRepository;
import com.debloopers.chibchaweb.security.UsuarioDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepo.findOptionalByCorreoUsuario(correo)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

        return new UsuarioDetails(usuario);
    }
}