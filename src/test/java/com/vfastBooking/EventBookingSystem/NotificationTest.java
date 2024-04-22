package com.vfastBooking.EventBookingSystem;

import com.vfastBooking.EventBookingSystem.dto.request.SendMailRequest;
import com.vfastBooking.EventBookingSystem.dto.response.SendEmailResponse;
import com.vfastBooking.EventBookingSystem.model.Event;
import com.vfastBooking.EventBookingSystem.model.User;
import com.vfastBooking.EventBookingSystem.service.NotificationService;
import com.vfastBooking.EventBookingSystem.service.ScheduleTask;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class NotificationTest {
    @Autowired
    private NotificationService notifictionService;
    @Autowired
    private ScheduleTask scheduleTask;
    @Test
    public void testSendNotificationToUserAsEmail(){
        List<User> user = List.of(new User("opeoluwaagnes@gmail.com","ope")
        ,new User("ogungbeniopemipo1@gmail.com","opemipo"));

        SendMailRequest sendMailRequest = new SendMailRequest();
        sendMailRequest.setUser(user);
        sendMailRequest.setDate(LocalDate.now());
        sendMailRequest.setEventName("women in tech");
        sendMailRequest.setSendEmail("tifeagnes86@gmail.com");

        SendEmailResponse emailResponse =  notifictionService.notifyUser(sendMailRequest);
        assertThat(emailResponse).isNotNull();

        scheduleTask.notifyUsers();
    }

}
