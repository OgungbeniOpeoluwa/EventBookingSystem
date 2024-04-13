package com.vfastBooking.EventBookingSystem;


import com.vfastBooking.EventBookingSystem.dto.request.CancelEventRequest;
import com.vfastBooking.EventBookingSystem.dto.request.CreateEventRequest;
import com.vfastBooking.EventBookingSystem.dto.request.EventBookingRequest;
import com.vfastBooking.EventBookingSystem.dto.response.BookedEventResponse;
import com.vfastBooking.EventBookingSystem.dto.response.BookingResponse;
import com.vfastBooking.EventBookingSystem.dto.response.CancelEventResponse;
import com.vfastBooking.EventBookingSystem.dto.response.CreateEventResponse;
import com.vfastBooking.EventBookingSystem.exception.*;
import com.vfastBooking.EventBookingSystem.model.Event;
import com.vfastBooking.EventBookingSystem.model.Ticket;
import com.vfastBooking.EventBookingSystem.model.TicketStatus;
import com.vfastBooking.EventBookingSystem.model.User;
import com.vfastBooking.EventBookingSystem.repository.EventRepository;
import com.vfastBooking.EventBookingSystem.service.EventService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest

public class EventTest {
    @Autowired
    private EventService eventService;
    @Autowired
    private EventRepository eventRepository;
    private CreateEventRequest eventRequest;
    @AfterEach
    public void delete(){
        eventRepository.deleteAll();
    }

    @BeforeEach
    public void doThis(){
        eventRequest = new CreateEventRequest();
        eventRequest.setEventName("shola conference");
        eventRequest.setCategory("conference");
        eventRequest.setDate("12/10/2024");
        eventRequest.setAvailableAttendeesCount(100);

    }

        @Test
    public void CreateEventTest() throws EventBookingException {
        CreateEventRequest eventRequest = new CreateEventRequest();
        eventRequest.setEventName("h2 conference");
        eventRequest.setCategory("conference");
        eventRequest.setDate("12/10/2024");
        eventRequest.setAvailableAttendeesCount(100);
            User user = new User("239","ope","opeoluwa@agnes","12345");

        CreateEventResponse response = eventService.createEvent(eventRequest,user);
        assertThat(response).isNotNull();
    }
    @Test
    public void testWrongInputOfDateThrowAnException(){
        CreateEventRequest eventRequest = new CreateEventRequest();
        eventRequest.setEventName("h2 conference");
        eventRequest.setCategory("conference");
        eventRequest.setDate("12/13/2024");
        User user = new User("239","ope","opeoluwa@agnes","12345");
        assertThrows(InvalidInputException.class,()->eventService.createEvent(eventRequest, user));

    }
    @Test
    public void findEventsTest() throws EventBookingException {
        User user = new User("239","ope","opeoluwa@agnes","12345");
        eventService.createEvent(eventRequest,user);
        List<Event> allEvents = eventService.findAllEvents();
        assertThat(allEvents).size().isEqualTo(1);
    }

    @Test
    public  void findEventsByNameThrowExceptionIfEventDoesntExitTest(){
        assertThrows(EventNotFoundException.class,()->eventService.findEventByName("Davido mili concert"));
    }
    @Test

    public void testBookingEvent() throws Exception {
        User user = new User("239","ope","opeoluwa@agnes","12345");
        eventService.createEvent(eventRequest,user);

        EventBookingRequest bookEvent = new EventBookingRequest();
        bookEvent.setEventName("shola conference");
        bookEvent.setNumberOfTicket(2);
        bookEvent.setEmail("opeoluwa@gmail.com");
        user = new User("239","ope","opeoluwa@agnes","12345");

        BookingResponse bookingResponse= eventService.bookEvent(bookEvent,user);
        assertThat(bookingResponse.getNumberOfSpaceReserved()).isEqualTo(2);
    }
    @Test
    public void testCancelingAReservationForAEvent() throws Exception {
        User user = new User("239","ope","opeoluwa@agnes","12345");

        eventService.createEvent(eventRequest,user);
        EventBookingRequest bookEvent = new EventBookingRequest();
        bookEvent.setEventName("shola conference");
        bookEvent.setNumberOfTicket(2);
        bookEvent.setEmail("opeoluwa@gmail.com");

        user = new User("239","ope","opeoluwa@agnes","12345");

        BookingResponse bookingResponse = eventService.bookEvent(bookEvent,user);

        CancelEventRequest cancelEventRequest = new CancelEventRequest();
        cancelEventRequest.setTicketId(bookingResponse.getTicketId());
        cancelEventRequest.setEventName("shola conference");

        CancelEventResponse eventResponse = eventService.cancelEvent(cancelEventRequest,user);

        Ticket ticket = eventService.findTicketById(eventResponse.getId(),"shola conference");

        assertThat(ticket.getStatus()).isEqualTo(TicketStatus.Cancelled);
    }
    @Test
    public void viewBookedTicket() throws Exception {
        User user = new User("239","ope","opeoluwa@agnes","12345");
        eventService.createEvent(eventRequest,user);
        EventBookingRequest bookEvent = new EventBookingRequest();
        bookEvent.setEventName("shola conference");
        bookEvent.setNumberOfTicket(2);
        bookEvent.setEmail("opeoluwa@gmail.com");

        user = new User("239","ope","opeoluwa@agnes","12345");

        BookingResponse bookingResponse = eventService.bookEvent(bookEvent,user);
        System.out.println(bookingResponse.getTicketId() );

        List<BookedEventResponse> bookedEvent = eventService.viewBookedEvents(user);
        System.out.println(bookedEvent);
        assertThat(bookedEvent.size()).isEqualTo(1);
    }

    @Test
    public void testThatWhenAUserBookAndTheReservationWasCancelTheUserDontExistInListOfUserForTheEvent() throws Exception {
        User user = new User("239","ope","opeoluwa@agnes","12345");

        eventService.createEvent(eventRequest,user);
        EventBookingRequest bookEvent = new EventBookingRequest();
        bookEvent.setEventName("shola conference");
        bookEvent.setNumberOfTicket(2);
        bookEvent.setEmail("opeoluwa@gmail.com");

        user = new User("239","ope","opeoluwa@agnes","12345");

        BookingResponse bookingResponse = eventService.bookEvent(bookEvent,user);
        assertThat(eventService.findBookedUserForTheEvent("shola conference").size()).isEqualTo(1);

        CancelEventRequest cancelEventRequest = new CancelEventRequest();
        cancelEventRequest.setTicketId(bookingResponse.getTicketId());
        cancelEventRequest.setEventName("shola conference");

        CancelEventResponse eventResponse = eventService.cancelEvent(cancelEventRequest,user);
        assertThat(eventService.findBookedUserForTheEvent("shola conference").size()).isEqualTo(0);

    }

}
