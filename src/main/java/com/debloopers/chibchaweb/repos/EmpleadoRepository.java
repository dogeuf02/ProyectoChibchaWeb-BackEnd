package com.debloopers.chibchaweb.repos;

import com.debloopers.chibchaweb.domain.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EmpleadoRepository extends JpaRepository<Empleado, String> {

    boolean existsByIdEmpleadoIgnoreCase(String idEmpleado);

}
