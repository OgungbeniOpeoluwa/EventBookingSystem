package com.vfastBooking.EventBookingSystem.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CancelEventRequest {
    private String eventName;
    private String ticketId;
    private int numberOfSeat;
}
