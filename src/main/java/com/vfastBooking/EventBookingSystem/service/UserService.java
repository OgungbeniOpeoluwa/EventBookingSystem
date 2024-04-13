package com.vfastBooking.EventBookingSystem.service;


import com.vfastBooking.EventBookingSystem.dto.request.RegisterRequest;
import com.vfastBooking.EventBookingSystem.dto.request.UserBookingRequest;
import com.vfastBooking.EventBookingSystem.dto.request.UserCancelBookingRequest;
import com.vfastBooking.EventBookingSystem.dto.request.UserCreateEventRequest;
import com.vfastBooking.EventBookingSystem.dto.response.*;
import com.vfastBooking.EventBookingSystem.exception.EventBookingException;

import java.util.List;

public interface UserService {
    ApiResponse<RegisterResponse> createAccount(RegisterRequest registerRequest) throws Exception;

    List<SearchResponse> searchForEvents()throws EventBookingException;

    BookingResponse bookEvent(UserBookingRequest bookEventRequest) throws Exception;

    CreateEventResponse createEvent(UserCreateEventRequest eventRequest) throws EventBookingException;

    CancelEventResponse CancelBookingReservation(UserCancelBookingRequest bookingRequests) throws EventBookingException;

    List<BookedEventResponse> viewReservations(String mail) throws  EventBookingException;

//      searchForEvents();
}
