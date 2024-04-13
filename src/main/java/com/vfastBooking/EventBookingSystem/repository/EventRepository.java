package com.vfastBooking.EventBookingSystem.repository;


import com.vfastBooking.EventBookingSystem.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EventRepository extends MongoRepository<Event,String> {
    Optional<Event> findByName(String eventName);
}
