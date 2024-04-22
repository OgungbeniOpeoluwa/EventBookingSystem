package com.vfastBooking.EventBookingSystem.util;


import com.vfastBooking.EventBookingSystem.exception.InvalidInputException;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Verification {

    public static LocalDate checkDate(String date) throws InvalidInputException {
        System.out.println(date);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try{
            return LocalDate.parse(date,dateTimeFormatter);
        }catch (DateTimeException e){
            throw new InvalidInputException("Invalid date format");
        }




    }
}
