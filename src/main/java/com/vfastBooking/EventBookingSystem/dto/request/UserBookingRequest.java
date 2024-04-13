package com.vfastBooking.EventBookingSystem.dto.request;

import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserBookingRequest {
    private String email;
    private String eventName;
    private int numberOfTicket;
}
