package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    private static final String SECRET_KEY = "55c73d9ce26056726f9285c52d5a62490c383037ce0ec7456035302363daccbc";

    public String generateToken(Usuario usuario) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("rol", usuario.getRol());
        claims.put("idUsuario", usuario.getIdUsuario());

        Integer idRelacionado = null;
        if (usuario.getAdmin() != null) {
            idRelacionado = usuario.getAdmin().getIdAdmin();
        } else if (usuario.getCliente() != null) {
            idRelacionado = usuario.getCliente().getIdCliente();
        } else if (usuario.getEmpleado() != null) {
            idRelacionado = usuario.getEmpleado().getIdEmpleado();
        } else if (usuario.getDistribuidor() != null) {
            idRelacionado = usuario.getDistribuidor().getIdDistribuidor();
        }

        claims.put("idRelacionado", idRelacionado);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(usuario.getCorreoUsuario())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Date extractExpiration(String token) {
        return extractClaims(token).getExpiration();
    }

    private Key getKey() {
        byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}