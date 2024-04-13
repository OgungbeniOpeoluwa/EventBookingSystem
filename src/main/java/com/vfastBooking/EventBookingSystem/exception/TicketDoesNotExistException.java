package com.vfastBooking.EventBookingSystem.exception;

public class TicketDoesNotExistException extends EventBookingException{
    public TicketDoesNotExistException(String message) {
        super(message);
    }
}
