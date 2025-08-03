package com.debloopers.chibchaweb.repository;

import com.debloopers.chibchaweb.entity.Empleado;
import com.debloopers.chibchaweb.entity.HistorialTicket;
import com.debloopers.chibchaweb.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistorialTicketRepository extends JpaRepository<HistorialTicket, Integer> {

    HistorialTicket findFirstByTicket(Ticket ticket);

    HistorialTicket findFirstByEmpleadoRealizador(Empleado empleado);

    HistorialTicket findFirstByEmpleadoReceptor(Empleado empleado);

}