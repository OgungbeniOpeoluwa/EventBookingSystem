package com.vfastBooking.EventBookingSystem.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CreateEventRequest {
    private String eventName;
    private String  date;
    private Integer availableAttendeesCount;
    private String eventDescription;
    private String category;
}
