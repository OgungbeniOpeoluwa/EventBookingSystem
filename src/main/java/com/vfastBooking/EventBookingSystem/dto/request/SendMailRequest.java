package com.vfastBooking.EventBookingSystem.dto.request;

import com.vfastBooking.EventBookingSystem.model.Event;
import com.vfastBooking.EventBookingSystem.model.User;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SendMailRequest {
    private String sendEmail;
    private List<User> user;
    private String eventName;
    private LocalDate date;

    public SendMailRequest(Event event){
        this.setSendEmail(event.getOrganizer().getEmail());
        this.setUser(event.getBookedUser());
        this.eventName = event.getName();
        this.date = event.getDate();
    }
}
