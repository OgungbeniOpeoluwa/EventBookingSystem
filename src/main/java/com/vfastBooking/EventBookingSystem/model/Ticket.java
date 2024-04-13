package com.vfastBooking.EventBookingSystem.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
@Setter
@Getter
@ToString

public class Ticket {
    private String id;
    private int numberOfSpaceReserved;
    @Field(name="TicketStatus")
    private TicketStatus status;
    private String  ownerEmail;
    private String ticketId;
    private Event event;
}
