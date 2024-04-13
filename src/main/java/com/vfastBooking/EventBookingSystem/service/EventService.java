package com.vfastBooking.EventBookingSystem.service;


import com.vfastBooking.EventBookingSystem.dto.request.CancelEventRequest;
import com.vfastBooking.EventBookingSystem.dto.request.CreateEventRequest;
import com.vfastBooking.EventBookingSystem.dto.request.EventBookingRequest;
import com.vfastBooking.EventBookingSystem.dto.response.BookedEventResponse;
import com.vfastBooking.EventBookingSystem.dto.response.BookingResponse;
import com.vfastBooking.EventBookingSystem.dto.response.CancelEventResponse;
import com.vfastBooking.EventBookingSystem.dto.response.CreateEventResponse;
import com.vfastBooking.EventBookingSystem.exception.EventBookingException;
import com.vfastBooking.EventBookingSystem.exception.EventCapcityException;
import com.vfastBooking.EventBookingSystem.exception.EventCategoryException;
import com.vfastBooking.EventBookingSystem.exception.EventNotFoundException;
import com.vfastBooking.EventBookingSystem.model.Event;
import com.vfastBooking.EventBookingSystem.model.Ticket;
import com.vfastBooking.EventBookingSystem.model.User;

import java.io.IOException;
import java.util.List;

public interface EventService {
    CreateEventResponse createEvent(CreateEventRequest eventRequest, User first) throws EventBookingException;

    List<Event> findAllEvents();


    Event findEventByName(String eventName) throws EventNotFoundException;

    BookingResponse bookEvent(EventBookingRequest bookEvent, User user) throws EventBookingException,IOException;

    CancelEventResponse cancelEvent(CancelEventRequest cancelEventRequest, User last) throws EventBookingException;

    Ticket findTicketById(String id, String eventName) throws EventBookingException;

    List<BookedEventResponse> viewBookedEvents(User last);

    List<User> findBookedUserForTheEvent(String sholaConference) throws EventNotFoundException;
}
