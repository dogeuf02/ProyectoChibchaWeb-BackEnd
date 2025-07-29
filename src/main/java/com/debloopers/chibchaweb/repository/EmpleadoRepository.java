package com.debloopers.chibchaweb.repository;

import com.debloopers.chibchaweb.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
}
