package com.vfastBooking.EventBookingSystem.dto.response;


import com.vfastBooking.EventBookingSystem.model.TicketStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CancelEventResponse {
    private TicketStatus status;
    private String id;
    private int numberOfSeat;
}
