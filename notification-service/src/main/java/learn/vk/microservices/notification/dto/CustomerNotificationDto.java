package learn.vk.microservices.notification.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CustomerNotificationDto {
    private Long id;
    private String name;
    private String email;
}
