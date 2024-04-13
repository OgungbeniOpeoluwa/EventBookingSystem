package com.vfastBooking.EventBookingSystem.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vfastBooking.EventBookingSystem.dto.request.*;
import com.vfastBooking.EventBookingSystem.dto.response.ApiResponse;
import com.vfastBooking.EventBookingSystem.dto.response.BookingResponse;
import com.vfastBooking.EventBookingSystem.dto.response.RegisterResponse;
import com.vfastBooking.EventBookingSystem.model.Event;
import com.vfastBooking.EventBookingSystem.model.EventCategory;
import com.vfastBooking.EventBookingSystem.model.User;
import com.vfastBooking.EventBookingSystem.repository.EventRepository;
import com.vfastBooking.EventBookingSystem.repository.UserRepository;
import com.vfastBooking.EventBookingSystem.service.EventService;
import com.vfastBooking.EventBookingSystem.service.UserService;
import lombok.experimental.Accessors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc

public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventRepository eventRepository;


    @AfterEach
    public void doThis(){
     userRepository.deleteAll();
     eventRepository.deleteAll();
    }
    private BookingResponse bookingResponse;
    @BeforeEach
    public   void  doThisCreate() throws Exception {
        userService.createAccount
                (new RegisterRequest("opeoluwa","ope@gmail.com","opemip@132"));
        userService.createEvent(new UserCreateEventRequest(
              "ope@gmail.com","Women in tech","12/12/2024",100,"join women together",
                "Conference"
                ));
        bookingResponse = userService.bookEvent(
                new UserBookingRequest("ope@gmail.com","Women in tech",13)
        );
    }

    @Test
    public void testUserCreateAccountEndPoint() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("ope@gmail.com");
        registerRequest.setName("opemipo ao");
        registerRequest.setPassword("12345@13");


        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/account")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(registerRequest)))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @Test
    public void testUserCreateEvent() throws Exception {
        UserCreateEventRequest eventRequest = new UserCreateEventRequest();
        eventRequest.setCategory("Conference");
        eventRequest.setEventDescription("Work all work");
        eventRequest.setDate("10/10/2024");
        eventRequest.setEmail("ope@gmail.com");
        eventRequest.setEventName("Boot camp");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/event")
                .content(mapper.writeValueAsBytes(eventRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }
    @Test
    public void testBookEventEndPoint() throws Exception {
        UserBookingRequest bookingRequest = new UserBookingRequest();
        bookingRequest.setEventName("Women in tech");
        bookingRequest.setNumberOfTicket(13);
        bookingRequest.setEmail("ope@gmail.com");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/bookEvent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(bookingRequest)))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }
    @Test
    public void testCancelEBookedEvent() throws Exception {
        UserCancelBookingRequest cancelBookingRequest = new UserCancelBookingRequest();
        cancelBookingRequest.setTicketId(bookingResponse.getTicketId());
        cancelBookingRequest.setEventName("Women in tech");
        cancelBookingRequest.setEmail("ope@gmail.com");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/cancel")
                .content(mapper.writeValueAsBytes(cancelBookingRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }
    @Test
    public void testViewReservation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/view_reservation/ope@gmail.com")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andDo(print());
    }


}
