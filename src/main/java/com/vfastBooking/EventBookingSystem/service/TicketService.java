package com.vfastBooking.EventBookingSystem.service;


import com.vfastBooking.EventBookingSystem.dto.request.EventBookingRequest;
import com.vfastBooking.EventBookingSystem.exception.EventBookingException;
import com.vfastBooking.EventBookingSystem.exception.TicketDoesNotExistException;
import com.vfastBooking.EventBookingSystem.model.Event;
import com.vfastBooking.EventBookingSystem.model.Ticket;

import java.io.IOException;

public interface TicketService {
    Ticket createTicket(EventBookingRequest bookEvent, Event event) throws IOException;

    Ticket findTicketById(String id, Event event) throws EventBookingException;

    Ticket cancelTicket(String ticketId, Event event) throws TicketDoesNotExistException;

    Ticket updateTicket(String id, int numberOfSeat,Event event) throws EventBookingException;
}
