package com.vfastBooking.EventBookingSystem.service;

import com.vfastBooking.EventBookingSystem.dto.request.SendMailRequest;
import com.vfastBooking.EventBookingSystem.dto.response.SendEmailResponse;
import com.vfastBooking.EventBookingSystem.model.Event;

public interface NotificationService {
    SendEmailResponse notifyUser(SendMailRequest sendMailRequest);
}
