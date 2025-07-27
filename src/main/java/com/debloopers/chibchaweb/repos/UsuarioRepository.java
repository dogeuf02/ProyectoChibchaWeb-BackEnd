package com.debloopers.chibchaweb.repos;

import com.debloopers.chibchaweb.domain.Administrador;
import com.debloopers.chibchaweb.domain.ClienteDirecto;
import com.debloopers.chibchaweb.domain.Empleado;
import com.debloopers.chibchaweb.domain.Registrador;
import com.debloopers.chibchaweb.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findFirstByCliente(ClienteDirecto clienteDirecto);

    Usuario findFirstByAdmin(Administrador administrador);

    Usuario findFirstByRegistrador(Registrador registrador);

    Usuario findFirstByEmpleado(Empleado empleado);

    Usuario findByCorreoUsuario(String correoUsuario);
}
