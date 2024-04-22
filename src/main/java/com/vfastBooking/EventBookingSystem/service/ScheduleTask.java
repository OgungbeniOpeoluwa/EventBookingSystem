package com.vfastBooking.EventBookingSystem.service;

import com.vfastBooking.EventBookingSystem.dto.request.SendMailRequest;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@EnableScheduling
@AllArgsConstructor
@Service
public class ScheduleTask {
    private EventService eventService;
    private ModelMapper mapper;
    private NotificationService notificationService;

    @Scheduled(cron = "0 28 11 ? * *")
    public void notifyUsers(){
        eventService.findAllEventForTheNextDay().stream().forEach(event->{
                if (!event.getBookedUser().isEmpty()){
                    notificationService.notifyUser(new SendMailRequest(event));
                }});
    }


}
