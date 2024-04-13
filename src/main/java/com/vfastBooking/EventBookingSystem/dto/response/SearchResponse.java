package com.vfastBooking.EventBookingSystem.dto.response;

import com.vfastBooking.EventBookingSystem.model.Event;
import com.vfastBooking.EventBookingSystem.model.EventCategory;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Setter
@Getter
@ToString
public class SearchResponse {
    private String eventName;
    private int numberSeatAvailable;
    private EventCategory eventCategory;
    private LocalDate date;
    private String eventDescription;

    public  SearchResponse(Event event){
        eventName = event.getName();
        numberSeatAvailable = event.getAvailableAttendeesCount();
        eventCategory = event.getCategory();
        date = event.getDate();
        eventDescription = event.getEventDescription();
    }
}
