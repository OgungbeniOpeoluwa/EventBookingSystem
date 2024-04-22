package com.vfastBooking.EventBookingSystem.controller;

import com.vfastBooking.EventBookingSystem.dto.request.RegisterRequest;
import com.vfastBooking.EventBookingSystem.dto.request.UserBookingRequest;
import com.vfastBooking.EventBookingSystem.dto.request.UserCancelBookingRequest;
import com.vfastBooking.EventBookingSystem.dto.request.UserCreateEventRequest;
import com.vfastBooking.EventBookingSystem.dto.response.ApiResponse;
import com.vfastBooking.EventBookingSystem.dto.response.RegisterResponse;
import com.vfastBooking.EventBookingSystem.exception.EventBookingException;
import com.vfastBooking.EventBookingSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/account")
    ResponseEntity<?> createAccount(@RequestBody RegisterRequest registerRequest){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.createAccount(registerRequest));
        }catch (Exception e){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(e.getMessage(),false));
        }
    }

    @PostMapping("/event")
    ResponseEntity<?> createEvent(@RequestBody UserCreateEventRequest createEventRequest){
        try{
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(userService.createEvent(createEventRequest),true));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(e.getMessage(),false));
        }
    }

    @PostMapping("/bookEvent")
    ResponseEntity<?> bookEvent(@RequestBody UserBookingRequest bookingRequest){
        try{
            return  ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(userService.bookEvent(bookingRequest),true));
        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(e.getMessage(),false));
        }
    }
    @PostMapping("/cancel")
    ResponseEntity<?> cancel(@RequestBody UserCancelBookingRequest cancelBookingRequest){
        try{
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(userService.CancelBookingReservation(cancelBookingRequest),true));
        }catch (EventBookingException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ApiResponse<>(e.getMessage(), false));
        }
        }

        @GetMapping("/view_reservation/{email}")
    ResponseEntity<?> viewBookedEvent(@PathVariable(name = "email")String email){
        try{
            return ResponseEntity.status(HttpStatus.FOUND).body(
                    new ApiResponse<>(userService.viewReservations(email),true));
        }catch (EventBookingException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(e.getMessage(),false));
        }
        }


}
