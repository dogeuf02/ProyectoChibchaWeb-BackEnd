package com.debloopers.chibchaweb.repository;

import com.debloopers.chibchaweb.entity.ClienteDirecto;
import com.debloopers.chibchaweb.entity.Distribuidor;
import com.debloopers.chibchaweb.entity.Empleado;
import com.debloopers.chibchaweb.entity.Ticket;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TicketRepository extends JpaRepository<Ticket, String> {

    Ticket findFirstByDistribuidor(Distribuidor distribuidor);

    Ticket findFirstByCliente(ClienteDirecto clienteDirecto);

    Ticket findFirstByEmpleado(Empleado empleado);

    Ticket findFirstByHistorialTicketUsuarioEmpleadoes(Empleado empleado);

    List<Ticket> findAllByHistorialTicketUsuarioEmpleadoes(Empleado empleado);

    boolean existsByIdTicketIgnoreCase(String idTicket);

}
