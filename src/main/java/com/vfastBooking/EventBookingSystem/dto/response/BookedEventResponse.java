package com.vfastBooking.EventBookingSystem.dto.response;


import com.vfastBooking.EventBookingSystem.model.Event;
import com.vfastBooking.EventBookingSystem.model.TicketStatus;
import lombok.*;

import java.time.LocalDate;
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BookedEventResponse {
    private String nameOfEvent;
    private TicketStatus status = TicketStatus.Confirmed;
    private LocalDate date;

    public BookedEventResponse(Event event){
        nameOfEvent = event.getName();
        date = event.getDate();

    }
}
