package com.vfastBooking.EventBookingSystem.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Document

@ToString
public class Event {
    @Id
    private String id;
    private String name;
    private LocalDate date;
    private Integer availableAttendeesCount;
    private String eventDescription;
    @Field(name="EventCategory")
    private EventCategory category;
//    @DocumentReference
    private List<User> bookedUser = new ArrayList<>();
    private User organizer;



}
