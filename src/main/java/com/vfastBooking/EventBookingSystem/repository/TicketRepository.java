package com.vfastBooking.EventBookingSystem.repository;


import com.vfastBooking.EventBookingSystem.model.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TicketRepository extends MongoRepository<Ticket,String> {
    List<Ticket> findByEventId(String eventId);

    Ticket findByTicketId(String ticketId);
}
