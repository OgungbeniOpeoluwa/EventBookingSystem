package com.vfastBooking.EventBookingSystem.dto.response;


import com.vfastBooking.EventBookingSystem.model.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {
    private String ticketId;
    private int numberOfSpaceReserved;
    private TicketStatus status;
    private String nameOfEvent;
    private LocalDate localDate;
}
