package learn.vk.microservices.notification.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import learn.vk.microservices.notification.dto.NotificationDto;
import learn.vk.microservices.notification.entity.Notification;
import learn.vk.microservices.notification.service.NotificationService;
import lombok.extern.log4j.Log4j;
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

        NotificationDto notificationDto = objectMapper.readValue(message, NotificationDto.class);

        Notification notification = new Notification();
        notification.setEmail(notificationDto.getCustomerEmail());
        notification.setMessage(
                String.format("Your reservation is %s. Details are: %s", notificationDto.getStatus(), message));

        notificationService.sendNotification(notification);

        log.info("Notification sent to customer: " + notificationDto.getCustomerEmail());
    }

}
