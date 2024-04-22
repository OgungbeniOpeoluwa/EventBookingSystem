package com.vfastBooking.EventBookingSystem.util;

import com.vfastBooking.EventBookingSystem.dto.response.BookingResponse;
import com.vfastBooking.EventBookingSystem.model.Event;
import com.vfastBooking.EventBookingSystem.model.Ticket;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

public class Mapper {

    public static final  String TEXTSUBJECT = "REMINDER";
    public static BookingResponse map(Ticket ticket, Event event){
        BookingResponse bookingResponse = new BookingResponse();

        bookingResponse.setNumberOfSpaceReserved(ticket.getNumberOfSpaceReserved());
        bookingResponse.setTicketId(ticket.getTicketId());
        bookingResponse.setStatus(ticket.getStatus());
        bookingResponse.setNameOfEvent(event.getName());
        bookingResponse.setLocalDate(event.getDate());
        return bookingResponse;
    }
    public static String createMessage(String eventName, LocalDate date){
        String message = String.format("""
                <html><head></head><body>
                Hello
                    <p>This is a quick reminder of the event booked for tomorrow.</p>
                    
                    Date: %s%n               
                    
                    Event name:%s
                    </body></html>
                """,eventName,date);
        return message;
    }
}
