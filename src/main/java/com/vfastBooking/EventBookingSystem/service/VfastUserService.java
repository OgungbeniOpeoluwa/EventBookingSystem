package com.vfastBooking.EventBookingSystem.service;


import com.vfastBooking.EventBookingSystem.dto.request.*;
import com.vfastBooking.EventBookingSystem.dto.response.*;
import com.vfastBooking.EventBookingSystem.exception.*;
import com.vfastBooking.EventBookingSystem.model.Event;
import com.vfastBooking.EventBookingSystem.model.User;
import com.vfastBooking.EventBookingSystem.repository.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor

public class VfastUserService implements UserService{

    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private EventService eventService;
    private Validator validator;
    @Override
    public ApiResponse<RegisterResponse> createAccount(@Valid RegisterRequest registerRequest) throws EventBookingException {
      Set<ConstraintViolation<RegisterRequest>> violation = validator.validate(registerRequest);
      if (!violation.isEmpty())throw new InvalidInputException(violation.stream().findAny().get().getMessage());

      Optional<User> user = findUserByEmail(registerRequest.getEmail());

          if (user.isPresent()) throw new UserNotFoundException("User already exist");

          User users = modelMapper.map(registerRequest, User.class);
          userRepository.save(users);
          RegisterResponse response = new RegisterResponse(users.getEmail());
          return new ApiResponse<>(response, true);

    }

    @Override
    public List<SearchResponse> searchForEvents() {
        List<Event> event = eventService.findAllEvents();
        List<SearchResponse> searchResponses = event.stream()
                .map(SearchResponse::new).toList();
        return searchResponses;
    }

    @Override
    public BookingResponse bookEvent(UserBookingRequest bookEventRequest) throws EventBookingException, IOException {
        Optional<User> user = findUserByEmail(bookEventRequest.getEmail());
        EventBookingRequest bookingRequest = modelMapper.map(bookEventRequest,EventBookingRequest.class);

        return eventService.bookEvent(bookingRequest,user.orElseThrow(()->new UserNotFoundException("User doesn't exist")));
    }

    @Override
    public CreateEventResponse createEvent(UserCreateEventRequest eventRequest) throws EventBookingException {
        Optional<User> user = findUserByEmail(eventRequest.getEmail());
        CreateEventRequest createEventRequest = modelMapper.map(eventRequest,CreateEventRequest.class);

        return eventService.createEvent(createEventRequest,user
                .orElseThrow(()->new UserNotFoundException("User doesn't exist")));
    }

    @Override
    public CancelEventResponse CancelBookingReservation(UserCancelBookingRequest bookingRequests) throws EventBookingException {
        Optional<User> user = findUserByEmail(bookingRequests.getEmail());
        CancelEventRequest cancelEventRequest = modelMapper.map(bookingRequests,CancelEventRequest.class);
        return eventService.cancelEvent(cancelEventRequest,user
                .orElseThrow(()->new UserNotFoundException("User doesn't exist ")));
    }

    @Override
    public List<BookedEventResponse> viewReservations(String mail) throws UserNotFoundException {
        Optional<User> user = findUserByEmail(mail);
        return eventService.viewBookedEvents(user.orElseThrow(()->
                new UserNotFoundException("user with email "+ mail +" doesn't exist")));
    }

    private Optional<User> findUserByEmail(String email){
        return userRepository.findByEmail(email);

    }


}
