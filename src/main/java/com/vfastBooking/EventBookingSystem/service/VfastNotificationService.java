package com.vfastBooking.EventBookingSystem.service;

import com.vfastBooking.EventBookingSystem.dto.request.BrevoSendEmail;
import com.vfastBooking.EventBookingSystem.dto.request.SendMailRequest;
import com.vfastBooking.EventBookingSystem.dto.request.UserDetails;
import com.vfastBooking.EventBookingSystem.dto.response.SendEmailResponse;
import com.vfastBooking.EventBookingSystem.model.Event;
import com.vfastBooking.EventBookingSystem.util.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VfastNotificationService implements NotificationService{
    @Autowired
    private ModelMapper mapper;


    @Value("${API_KEY}")
    private String apiKey;
    @Value("${BREVO_URL}")
    private String url;

    @Override
    public SendEmailResponse notifyUser(SendMailRequest sendMailRequest) {
        UserDetails sender = new UserDetails();

        sender.setEmail(sendMailRequest.getSendEmail());

        List<UserDetails> recipient = sendMailRequest.getUser()
                .stream().map(x-> mapper.map(x,UserDetails.class)).collect(Collectors.toList());

        String subject = Mapper.createMessage(sendMailRequest.getEventName(),sendMailRequest.getDate());

        BrevoSendEmail sendEmail = new BrevoSendEmail();
        sendEmail.setSender(sender);
        sendEmail.setTo(recipient);
        sendEmail.setSubject(Mapper.TEXTSUBJECT);
        sendEmail.setHtmlContent(subject);



        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.ACCEPT,"application/json");
        headers.set("api-key",apiKey);

        HttpEntity <?> entity = new HttpEntity(sendEmail,headers);

        ResponseEntity<SendEmailResponse> response = restTemplate.postForEntity(url,entity,SendEmailResponse.class);
        return response.getBody();
    }
}
