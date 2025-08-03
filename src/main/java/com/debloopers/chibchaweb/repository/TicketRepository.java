package com.debloopers.chibchaweb.repository;

import com.debloopers.chibchaweb.entity.ClienteDirecto;
import com.debloopers.chibchaweb.entity.Distribuidor;
import com.debloopers.chibchaweb.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TicketRepository extends JpaRepository<Ticket, String> {

    Ticket findFirstByDistribuidor(Distribuidor distribuidor);

    Ticket findFirstByCliente(ClienteDirecto clienteDirecto);

    boolean existsByIdTicketIgnoreCase(String idTicket);

}
