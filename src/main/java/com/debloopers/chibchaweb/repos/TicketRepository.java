package com.debloopers.chibchaweb.repos;

import com.debloopers.chibchaweb.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TicketRepository extends JpaRepository<Ticket, Integer> {
}
