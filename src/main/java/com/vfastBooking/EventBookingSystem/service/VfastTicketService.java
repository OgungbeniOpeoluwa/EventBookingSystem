package com.vfastBooking.EventBookingSystem.service;


import com.fasterxml.jackson.databind.ObjectMapper;

import com.vfastBooking.EventBookingSystem.dto.request.EventBookingRequest;
import com.vfastBooking.EventBookingSystem.exception.EventBookingException;
import com.vfastBooking.EventBookingSystem.exception.TicketDoesNotExistException;
import com.vfastBooking.EventBookingSystem.model.Event;
import com.vfastBooking.EventBookingSystem.model.Ticket;
import com.vfastBooking.EventBookingSystem.model.TicketStatus;
import com.vfastBooking.EventBookingSystem.repository.TicketRepository;
import com.vfastBooking.EventBookingSystem.util.UniqueId;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class VfastTicketService implements TicketService{
    private TicketRepository ticketRepository;
    @Override
    public Ticket createTicket(EventBookingRequest bookEvent, Event event) throws IOException {
        Ticket ticket = new Ticket();
        ticket.setNumberOfSpaceReserved(bookEvent.getNumberOfTicket());
        ticket.setStatus(TicketStatus.Confirmed);
        ticket.setEvent(event);
        ticket.setOwnerEmail(bookEvent.getEmail());
        ticket.setTicketId(bookEvent.getEventName() +"-"+generateTicketId());
        ticketRepository.save(ticket);
        return ticket;
    }

    @Override
    public Ticket findTicketById(String ticketId,Event event) throws TicketDoesNotExistException {
        List<Ticket> tickets = ticketRepository.findByEventId(event.getId());
       Ticket ticket =  tickets.stream().filter(x->x.getTicketId().equals(ticketId))
                .findAny().orElseThrow(()->new TicketDoesNotExistException("Ticket with the id doesn't exist"));
       return ticket;
    }

    @Override
    public Ticket cancelTicket(String ticketId, Event event) throws TicketDoesNotExistException {
        Ticket ticket = findTicketById(ticketId,event);
        ticket.setStatus(TicketStatus.Cancelled);
        ticket.setNumberOfSpaceReserved(0);
        ticket.setEvent(event);
        ticketRepository.save(ticket);
        return ticket;
    }

    @Override
    public Ticket updateTicket(String id, int numberOfSeat,Event event) throws EventBookingException {
      Ticket  ticket = findTicketById(id,event);
        ticket.setNumberOfSpaceReserved(ticket.getNumberOfSpaceReserved()-numberOfSeat);
        ticketRepository.save(ticket);
        return ticket;
    }

    private String  generateTicketId() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        Map<String,Integer> value = new HashMap<>();
        value.put("length",4);
        HttpEntity<?> length = new HttpEntity<>(objectMapper.writeValueAsString(value),httpHeaders);
      ResponseEntity<UniqueId> values = restTemplate.exchange
              ("http://localhost:5050/numeric", HttpMethod.POST,length, UniqueId.class);
        return values.getBody().getRes() ;

    }
}
