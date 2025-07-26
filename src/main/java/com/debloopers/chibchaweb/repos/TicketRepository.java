package com.debloopers.chibchaweb.repos;

import com.debloopers.chibchaweb.domain.ClienteDirecto;
import com.debloopers.chibchaweb.domain.Distribuidor;
import com.debloopers.chibchaweb.domain.Empleado;
import com.debloopers.chibchaweb.domain.Ticket;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TicketRepository extends JpaRepository<Ticket, String> {

    Ticket findFirstByCliente(ClienteDirecto clienteDirecto);

    Ticket findFirstByNombreTipoDoc(Distribuidor distribuidor);

    Ticket findFirstByEmpleado(Empleado empleado);

    Ticket findFirstBySolucionEmpleadoes(Empleado empleado);

    List<Ticket> findAllBySolucionEmpleadoes(Empleado empleado);

    boolean existsByIdTicketIgnoreCase(String idTicket);

}
