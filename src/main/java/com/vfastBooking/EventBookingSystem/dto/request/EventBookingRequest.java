package com.vfastBooking.EventBookingSystem.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class EventBookingRequest {
    private String eventName;
    private int numberOfTicket;
    private String email;
}
