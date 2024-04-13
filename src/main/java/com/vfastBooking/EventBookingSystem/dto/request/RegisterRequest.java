package com.vfastBooking.EventBookingSystem.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

import static com.vfastBooking.EventBookingSystem.exception.GlobalException.NAMEEXCEPTION;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @Size(min = 5,max = 100, message =  NAMEEXCEPTION)
    private String name;
    @Email(message = "Invalid Email")
    private String email;
    @Size(min = 8, max = 20)
    private String password;
}
