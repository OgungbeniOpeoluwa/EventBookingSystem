package com.vfastBooking.EventBookingSystem.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateEventRequest {
    private String email;
    private String eventName;
    private String  date;
    private Integer availableAttendeesCount;
    private String eventDescription;
    private String category;
}
