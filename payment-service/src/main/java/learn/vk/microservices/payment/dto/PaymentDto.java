package learn.vk.microservices.payment.dto;

import lombok.Data;

@Data
public class PaymentDto {
    private Long id;
    private Long reservationId;
    private Double amount;
}
