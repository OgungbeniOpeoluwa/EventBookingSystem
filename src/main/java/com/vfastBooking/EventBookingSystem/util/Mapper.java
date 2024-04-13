package com.vfastBooking.EventBookingSystem.util;

import com.vfastBooking.EventBookingSystem.dto.response.BookingResponse;
import com.vfastBooking.EventBookingSystem.model.Event;
import com.vfastBooking.EventBookingSystem.model.Ticket;

public class Mapper {
    public static BookingResponse map(Ticket ticket, Event event){
        BookingResponse bookingResponse = new BookingResponse();

        bookingResponse.setNumberOfSpaceReserved(ticket.getNumberOfSpaceReserved());
        bookingResponse.setTicketId(ticket.getTicketId());
        bookingResponse.setStatus(ticket.getStatus());
        bookingResponse.setNameOfEvent(event.getName());
        bookingResponse.setLocalDate(event.getDate());
        return bookingResponse;
    }
}
