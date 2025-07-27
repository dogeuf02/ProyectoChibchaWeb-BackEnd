package com.debloopers.chibchaweb.repos;

import com.debloopers.chibchaweb.domain.ClienteDirecto;
import com.debloopers.chibchaweb.domain.Distribuidor;
import com.debloopers.chibchaweb.domain.Empleado;
import com.debloopers.chibchaweb.domain.Ticket;
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
