package learn.vk.microservices.notification.dto;

import lombok.Data;

@Data
public class PaymentDto {
    private Long id;
    private Long reservationId;
    private Double amount;
}
