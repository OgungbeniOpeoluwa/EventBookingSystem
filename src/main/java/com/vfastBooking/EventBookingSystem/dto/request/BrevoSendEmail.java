package com.vfastBooking.EventBookingSystem.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class BrevoSendEmail {
    private UserDetails sender;
    private List<UserDetails> to;
    private String subject;
    private String htmlContent;
}
