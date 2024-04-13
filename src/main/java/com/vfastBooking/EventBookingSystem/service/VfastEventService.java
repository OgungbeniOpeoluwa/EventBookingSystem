package com.vfastBooking.EventBookingSystem.service;


import com.vfastBooking.EventBookingSystem.dto.request.CancelEventRequest;
import com.vfastBooking.EventBookingSystem.dto.request.CreateEventRequest;
import com.vfastBooking.EventBookingSystem.dto.request.EventBookingRequest;
import com.vfastBooking.EventBookingSystem.dto.response.BookedEventResponse;
import com.vfastBooking.EventBookingSystem.dto.response.BookingResponse;
import com.vfastBooking.EventBookingSystem.dto.response.CancelEventResponse;
import com.vfastBooking.EventBookingSystem.dto.response.CreateEventResponse;
import com.vfastBooking.EventBookingSystem.exception.*;
import com.vfastBooking.EventBookingSystem.model.Event;
import com.vfastBooking.EventBookingSystem.model.EventCategory;
import com.vfastBooking.EventBookingSystem.model.Ticket;
import com.vfastBooking.EventBookingSystem.model.User;
import com.vfastBooking.EventBookingSystem.repository.EventRepository;
import com.vfastBooking.EventBookingSystem.util.Mapper;
import com.vfastBooking.EventBookingSystem.util.Verification;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.vfastBooking.EventBookingSystem.util.Mapper.map;

@Service
@AllArgsConstructor
public class VfastEventService implements EventService{
    private EventRepository eventRepository;
    private ModelMapper modelMapper;
    private TicketService ticketService;
    @Override
    public CreateEventResponse createEvent(CreateEventRequest eventRequest, User organizer) throws EventBookingException {
        Event event = modelMapper.map(eventRequest,Event.class);
        LocalDate localDate = Verification.checkDate(eventRequest.getDate());
        EventCategory category = checkIfCategoryExist(eventRequest.getCategory());
        if(category == null)throw new EventCategoryException("Event category doesn't exist");
        event.setCategory(category);
        event.setDate(localDate);
        event.setOrganizer(organizer);
        eventRepository.save(event);
        return new CreateEventResponse(eventRequest.getEventName() + " "+ "has been created successfully");
    }
    private EventCategory checkIfCategoryExist(String category){
        for (EventCategory categories:EventCategory.values()) {
            if (categories.name().equalsIgnoreCase(category))return categories;
        }
        return null;
    }

    @Override
    public List<Event> findAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Event findEventByName(String eventName) throws EventNotFoundException {
        Optional<Event> event =  eventRepository.findByName(eventName);
       return event.orElseThrow(()->new EventNotFoundException(eventName +" "+ "doesn't exist"));

    }

    @Override
    public BookingResponse bookEvent(EventBookingRequest bookEvent, User user) throws EventBookingException, IOException {
        Event event = findEventByName(bookEvent.getEventName());

        if (event.getAvailableAttendeesCount().equals(0))throw new EventCapcityException("Events has been fully booked");

        if(checkIfUserExistInList(user,event.getName()))throw new UserExistException("you already have a reservation for this event");

         event.setAvailableAttendeesCount(event.getAvailableAttendeesCount()-bookEvent.getNumberOfTicket());

        event.getBookedUser().add(user);

        eventRepository.save(event);

        bookEvent.setEmail(user.getEmail());

        Ticket ticket = ticketService.createTicket(bookEvent,event);

        return map(ticket,event);
    }

    @Override
    public CancelEventResponse cancelEvent(CancelEventRequest cancelEventRequest, User user)throws EventBookingException {
        Event event = findEventByName(cancelEventRequest.getEventName());
        CancelEventResponse cancelEventResponse = new CancelEventResponse();


        if(!checkIfUserExistInList(user, event.getName()))throw new CancelEventException("No reservation for "+ user.getName());

        Ticket ticket =  ticketService.findTicketById(cancelEventRequest.getTicketId(),event);

        if(cancelEventRequest.getNumberOfSeat() != ticket.getNumberOfSpaceReserved()){
            ticket = ticketService.updateTicket(ticket.getTicketId(),cancelEventRequest.getNumberOfSeat(),event);
            cancelEventResponse.setId(ticket.getTicketId());
            cancelEventResponse.setStatus(ticket.getStatus());
            cancelEventResponse.setNumberOfSeat(ticket.getNumberOfSpaceReserved());
            return cancelEventResponse;}

        event.setBookedUser(event.getBookedUser().stream()
               .filter(x->!x.getEmail().equals(user.getEmail())).collect(Collectors.toList()));


        event.setAvailableAttendeesCount(event.getAvailableAttendeesCount()+ticket.getNumberOfSpaceReserved());

        eventRepository.save(event);

        ticket = ticketService.cancelTicket(cancelEventRequest.getTicketId(),event);

        cancelEventResponse.setId(ticket.getTicketId());
        cancelEventResponse.setStatus(ticket.getStatus());
        return cancelEventResponse;
    }

    @Override
    public Ticket findTicketById(String id,String eventName) throws EventBookingException {
        Event event = findEventByName(eventName);
        Ticket ticket = ticketService.findTicketById(id,event);
        return ticket;
    }

    @Override
    public List<BookedEventResponse> viewBookedEvents(User user) {
       List<BookedEventResponse> eventResponse = eventRepository.findAll().stream()
                .map(x->{
                  Optional<User> users =x.getBookedUser()
                          .stream().filter(y->y.getEmail()
                                  .equals(user.getEmail())).findAny();
                if(users.isEmpty()) {
                   return null;
                } return new BookedEventResponse(x);
                }).toList();
        return eventResponse;
    }

    @Override
    public List<User> findBookedUserForTheEvent(String eventName) throws EventNotFoundException {
        Event event = findEventByName(eventName);
        return event.getBookedUser();
    }

    private  boolean checkIfUserExistInList(User user, String eventName) throws EventNotFoundException {
        Event event = findEventByName(eventName);
        for(User users:event.getBookedUser()){
            if (users.getEmail().equals(user.getEmail()))return true;
        }
        return false;
    }
}
