package com.debloopers.chibchaweb.repository;

import com.debloopers.chibchaweb.entity.ClienteDirecto;
import com.debloopers.chibchaweb.entity.Distribuidor;
import com.debloopers.chibchaweb.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface TicketRepository extends JpaRepository<Ticket, String> {

    Ticket findFirstByDistribuidor(Distribuidor distribuidor);

    Ticket findFirstByCliente(ClienteDirecto clienteDirecto);

    @Query("SELECT t.idTicket FROM Ticket t WHERE t.idTicket LIKE 'TCK-%' ORDER BY LENGTH(t.idTicket) DESC, t.idTicket DESC LIMIT 1")
    String findLastTicketId();

}