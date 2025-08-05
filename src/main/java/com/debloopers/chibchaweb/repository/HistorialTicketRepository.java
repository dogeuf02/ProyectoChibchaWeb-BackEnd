package com.debloopers.chibchaweb.repository;

import com.debloopers.chibchaweb.entity.Empleado;
import com.debloopers.chibchaweb.entity.HistorialTicket;
import com.debloopers.chibchaweb.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistorialTicketRepository extends JpaRepository<HistorialTicket, Integer> {

    HistorialTicket findFirstByTicket(Ticket ticket);

    List<HistorialTicket> findByTicket_IdTicket(String idTicket);

    HistorialTicket findFirstByEmpleadoRealizador(Empleado empleado);

    HistorialTicket findFirstByEmpleadoReceptor(Empleado empleado);

}