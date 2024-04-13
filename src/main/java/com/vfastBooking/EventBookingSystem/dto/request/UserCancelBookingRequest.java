package com.vfastBooking.EventBookingSystem.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserCancelBookingRequest {
    private String email;
    private String eventName;
    private String ticketId;
    private int numberOfSeat;
}
