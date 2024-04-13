package com.vfastBooking.EventBookingSystem;

import com.vfastBooking.EventBookingSystem.dto.request.*;
import com.vfastBooking.EventBookingSystem.dto.response.*;

import com.vfastBooking.EventBookingSystem.exception.CancelEventException;
import com.vfastBooking.EventBookingSystem.exception.EventBookingException;
import com.vfastBooking.EventBookingSystem.exception.EventCapcityException;
import com.vfastBooking.EventBookingSystem.exception.InvalidInputException;
import com.vfastBooking.EventBookingSystem.model.Event;
import com.vfastBooking.EventBookingSystem.model.EventCategory;
import com.vfastBooking.EventBookingSystem.model.TicketStatus;
import com.vfastBooking.EventBookingSystem.model.User;
import com.vfastBooking.EventBookingSystem.repository.EventRepository;
import com.vfastBooking.EventBookingSystem.repository.UserRepository;
import com.vfastBooking.EventBookingSystem.service.EventService;
import com.vfastBooking.EventBookingSystem.service.UserService;
import com.vfastBooking.EventBookingSystem.service.VfastUserService;
import jakarta.validation.Validator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Validator validator;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private EventService eventService;
    @Autowired
    private EventRepository eventRepository;
    private RegisterRequest registerRequest;
    private Event event;
    @AfterEach
    public void doThisAfter(){
        eventRepository.deleteAll();

        userRepository.deleteAll();
    }
    @BeforeEach
    public  void  doThis(){
         event = new Event();
        event.setCategory(EventCategory.CONFERENCE);
        event.setDate(LocalDate.now());
        event.setAvailableAttendeesCount(13);
        event.setName("Women in tech");
        event.setEventDescription("brings all women together  for the future of tech");
        eventService = mock(EventService.class);
    }
    @BeforeEach
    public  void createUser(){
        registerRequest = new RegisterRequest();
        registerRequest.setPassword("12345@178");
        registerRequest.setName("opemipo");
        registerRequest.setEmail("ope@gmail.com");
    }
    @Test
    public void testUserCanCreateAnAccount() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("ope@gmail.com");
        registerRequest.setName("opeOLUWa");
        registerRequest.setPassword("ope@123678");

        ApiResponse<RegisterResponse> response = userService.createAccount(registerRequest);

        assertThat(response).isNotNull();
        assertThat(response.getResponse().getEmail()).isEqualTo("ope@gmail.com");
    }
    @Test
    public void testThatInvalidEmailInputThrowsException(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("opegmail.com");
        registerRequest.setName("ope");
        registerRequest.setPassword("ope@123");
        assertThrows(InvalidInputException.class,()->userService.createAccount(registerRequest));
    }
    @Test
    public void testThatCanSearchForEvent() throws EventBookingException {
        when(eventService.findAllEvents()).thenReturn(List.of(event));
        UserService service = new VfastUserService(userRepository,modelMapper,eventService,validator);
        List <SearchResponse> searchResponse = service.searchForEvents();
        assertThat(searchResponse.size()).isEqualTo(1);
    }
    @Test
    public void testBookingEvent() throws Exception {
        when(eventService.bookEvent(any(),any())).thenReturn(new BookingResponse(
                "1234",1, TicketStatus.Confirmed,"Women in tech",LocalDate.now()
        ));
        userService = new VfastUserService(userRepository,modelMapper,eventService,validator);

        userService.createAccount(registerRequest);


        UserBookingRequest bookEventRequest = new UserBookingRequest();
        bookEventRequest.setEventName("Women in tech");
        bookEventRequest.setEmail("ope@gmail.com");
        bookEventRequest.setNumberOfTicket(4);
        BookingResponse bookingResponse = userService.bookEvent(bookEventRequest);

        Mockito.verify(eventService,times(1)).bookEvent(any(),any());

        assertThat(bookingResponse.getTicketId()).isNotNull();
    }
    @Test
    public void testCreateEvent() throws Exception {
         userService.createAccount(registerRequest);

        CreateEventResponse eventResponse = createEventResponse("Women in tech","12/03/2025",150,"Game");


        assertThat(eventResponse).isNotNull();
    }

    @Test
    public void testUserCancelEvent() throws Exception {
        userService.createAccount(registerRequest);

        CreateEventResponse eventResponse = createEventResponse("Women in tech","12/03/2025",150,"Game");


        UserCancelBookingRequest bookingRequests = new UserCancelBookingRequest();
        bookingRequests.setEmail("ope@gmail.com");
        bookingRequests.setEventName("Women in tech");
        bookingRequests.setTicketId("Women in tech - 12345");
        assertThrows(CancelEventException.class,()->userService.CancelBookingReservation(bookingRequests));
    }
    @Test
    public void testViewBookedEvents() throws Exception {
        userService.createAccount(registerRequest);
        createEventResponse("Women in tech","12/03/2025",150,"Game");
        createEventResponse("Boot Camp","12/09/2024",50,"Conference");

        UserBookingRequest bookingRequest = new UserBookingRequest();
        bookingRequest.setNumberOfTicket(1);
        bookingRequest.setEventName("Women in tech");
        bookingRequest.setEmail("ope@gmail.com");

        BookingResponse bookingResponse = userService.bookEvent(bookingRequest);
        bookingRequest.setEventName("Boot Camp");
        bookingResponse = userService.bookEvent(bookingRequest);


         List<BookedEventResponse> events =  userService.viewReservations("ope@gmail.com");

         assertThat(events.size()).isEqualTo(2);
    }


    private CreateEventResponse createEventResponse(String eventName,String date,int attendeed,String category) throws EventBookingException {
        UserCreateEventRequest eventRequest = new UserCreateEventRequest();
        eventRequest.setEventName(eventName);
        eventRequest.setDate(date);
        eventRequest.setEventDescription("bring woman together");
        eventRequest.setEmail("ope@gmail.com");
        eventRequest.setAvailableAttendeesCount(attendeed);
        eventRequest.setCategory(category);

        return userService.createEvent(eventRequest);
    }

    @Test
    public void testThatWhenUserBookFor3SeatAndCancelOneTicketIsStillAvailableForTwo() throws Exception {
        userService.createAccount(registerRequest);

        createEventResponse("Women in tech","12/03/2025",150,"Game");

        UserBookingRequest bookingRequest = new UserBookingRequest();
        bookingRequest.setNumberOfTicket(10);
        bookingRequest.setEventName("Women in tech");
        bookingRequest.setEmail("ope@gmail.com");

        BookingResponse bookingResponse = userService.bookEvent(bookingRequest);

        UserCancelBookingRequest request = new UserCancelBookingRequest();
        request.setEmail("ope@gmail.com");
        request.setEventName("Women in tech");
        request.setTicketId(bookingResponse.getTicketId());
        request.setNumberOfSeat(3);

        CancelEventResponse eventResponse = userService.CancelBookingReservation(request);
        assertThat(eventResponse.getNumberOfSeat()).isEqualTo(7);



    }



}
