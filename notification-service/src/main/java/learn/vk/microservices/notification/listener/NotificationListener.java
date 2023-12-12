package learn.vk.microservices.notification.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import learn.vk.microservices.notification.dto.CustomerNotificationDto;
import learn.vk.microservices.notification.dto.ReservationNotificationDto;
import learn.vk.microservices.notification.entity.Notification;
import learn.vk.microservices.notification.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationListener {

    @Autowired
    NotificationService notificationService;

    @KafkaListener(topics = "ReservationConfirmed", groupId = "reservation-notification")
    @KafkaListener(topics = "ReservationFailed", groupId = "reservation-notification")
    @KafkaListener(topics = "PaymentFailed", groupId = "reservation-notification")
    public void reservationNotifications(String message) throws JsonProcessingException {
        log.info("Notification received: " + message);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

        ReservationNotificationDto reservationNotificationDto = objectMapper.readValue(message, ReservationNotificationDto.class);

        Notification notification = new Notification();
        notification.setEmail(reservationNotificationDto.getCustomerEmail());
        notification.setMessage(
                String.format("Your reservation is %s. Details are: %s", reservationNotificationDto.getStatus(), message));

        notificationService.sendNotification(notification);

        log.info("Notification sent to customer: " + reservationNotificationDto.getCustomerEmail());
    }

    @KafkaListener(topics = "CustomerCreated", groupId = "customer-profile-notification")
    public void customerProfileNotifications(String message) throws JsonProcessingException {
        log.info("Notification received: " + message);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        CustomerNotificationDto customerNotificationDto = objectMapper.readValue(message, CustomerNotificationDto.class);

        Notification notification = new Notification();
        notification.setEmail(customerNotificationDto.getEmail());
        notification.setMessage(
                String.format("Your profile is created. Details are: %s", message));

        notificationService.sendNotification(notification);

        log.info("Notification sent to customer: " + customerNotificationDto.toString());
    }

}
