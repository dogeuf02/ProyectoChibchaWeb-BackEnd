package com.debloopers.chibchaweb.repository;

import com.debloopers.chibchaweb.entity.Administrador;
import com.debloopers.chibchaweb.entity.ClienteDirecto;
import com.debloopers.chibchaweb.entity.Distribuidor;
import com.debloopers.chibchaweb.entity.Empleado;
import com.debloopers.chibchaweb.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findFirstByCliente(ClienteDirecto clienteDirecto);

    Usuario findFirstByAdmin(Administrador administrador);

    Usuario findFirstByEmpleado(Empleado empleado);

    Usuario findFirstByDistribuidor(Distribuidor distribuidor);

    Usuario findByCorreoUsuario(String correoUsuario);

    Optional<Usuario> findOptionalByCorreoUsuario(String correo);
}
