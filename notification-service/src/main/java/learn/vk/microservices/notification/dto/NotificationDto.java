package learn.vk.microservices.notification.dto;

import lombok.Data;

@Data
public class NotificationDto {
    private Long id;

    private String message;
    private String email;
}
