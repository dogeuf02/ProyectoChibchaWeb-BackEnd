package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.dto.TokenVerificacionDTO;
import com.debloopers.chibchaweb.entity.TokenVerificacion;
import com.debloopers.chibchaweb.entity.Usuario;
import com.debloopers.chibchaweb.repository.TokenVerificacionRepository;
import com.debloopers.chibchaweb.repository.UsuarioRepository;
import com.debloopers.chibchaweb.util.NotFoundException;
import com.debloopers.chibchaweb.util.ReferencedWarning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TokenVerificacionService {

    @Autowired
    private TokenVerificacionRepository tokenVerificacionRepository;

    //
    private final UsuarioRepository usuarioRepository;

    //
    public TokenVerificacionService(
            final TokenVerificacionRepository tokenVerificacionRepository,
            final UsuarioRepository usuarioRepository) {
        this.tokenVerificacionRepository = tokenVerificacionRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public TokenVerificacion crearTokenParaUsuario(Usuario usuario) {
        String token = UUID.randomUUID().toString();
        LocalDateTime expiracion = LocalDateTime.now().plusHours(24);

        TokenVerificacion nuevoToken = new TokenVerificacion();
        nuevoToken.setToken(token);
        nuevoToken.setUsuario(usuario);
        nuevoToken.setFechaExpiracion(expiracion);
        nuevoToken.setEstado(false);

        return tokenVerificacionRepository.save(nuevoToken);
    }

    public Usuario validarToken(String token) {
        TokenVerificacion encontrado = tokenVerificacionRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid or not found token."));

        if (encontrado.getFechaExpiracion().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("The token has expired.");
        }

        encontrado.setEstado(true);
        tokenVerificacionRepository.save(encontrado);

        return encontrado.getUsuario();
    }

    //
    public List<TokenVerificacionDTO> findAll() {
        return tokenVerificacionRepository.findAll().stream()
                .map(this::mapToDTO)
                .toList();
    }

    public TokenVerificacionDTO get(final Long id) {
        return tokenVerificacionRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new NotFoundException("Token no encontrado"));
    }

    public Long create(final TokenVerificacionDTO dto) {
        final TokenVerificacion token = new TokenVerificacion();
        mapToEntity(dto, token);
        return tokenVerificacionRepository.save(token).getId();
    }

    public void update(final Long id, final TokenVerificacionDTO dto) {
        final TokenVerificacion token = tokenVerificacionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Token no encontrado"));
        mapToEntity(dto, token);
        tokenVerificacionRepository.save(token);
    }

    public void delete(final Long id) {
        tokenVerificacionRepository.deleteById(id);
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final TokenVerificacion token = tokenVerificacionRepository.findById(id)
                .orElse(null);
        if (token != null && token.getUsuario() != null) {
            ReferencedWarning warning = new ReferencedWarning();
            warning.setKey("tokenVerificacion.usuario.referenced");
            warning.addParam(token.getUsuario().getIdUsuario());
            return warning;
        }
        return null;
    }

    private TokenVerificacionDTO mapToDTO(final TokenVerificacion token) {
        TokenVerificacionDTO dto = new TokenVerificacionDTO();
        dto.setId(token.getId());
        dto.setToken(token.getToken());
        dto.setFechaExpiracion(token.getFechaExpiracion());
        dto.setIdUsuario(token.getUsuario().getIdUsuario());
        dto.setEstado(token.getEstado());
        return dto;
    }

    private void mapToEntity(final TokenVerificacionDTO dto, final TokenVerificacion token) {
        token.setToken(dto.getToken());
        token.setFechaExpiracion(dto.getFechaExpiracion());
        token.setEstado(dto.isEstado());

        final Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        token.setUsuario(usuario);
    }
}