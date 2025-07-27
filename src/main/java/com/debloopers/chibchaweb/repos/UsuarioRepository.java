package com.debloopers.chibchaweb.repos;

import com.debloopers.chibchaweb.domain.Administrador;
import com.debloopers.chibchaweb.domain.ClienteDirecto;
import com.debloopers.chibchaweb.domain.Distribuidor;
import com.debloopers.chibchaweb.domain.Empleado;
import com.debloopers.chibchaweb.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findFirstByCliente(ClienteDirecto clienteDirecto);

    Usuario findFirstByAdmin(Administrador administrador);

    Usuario findFirstByEmpleado(Empleado empleado);

    Usuario findFirstByDistribuidor(Distribuidor distribuidor);

    Usuario findByCorreoUsuario(String correoUsuario);
}
