package com.vfastBooking.EventBookingSystem.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ViewBookedEventRequest {
    private String email;
}
