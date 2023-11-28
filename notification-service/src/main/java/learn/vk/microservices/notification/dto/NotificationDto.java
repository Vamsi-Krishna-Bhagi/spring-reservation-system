package learn.vk.microservices.notification.dto;

import lombok.Data;

@Data
public class NotificationDto {
    private Long productId;
    private Long quantity;
}
