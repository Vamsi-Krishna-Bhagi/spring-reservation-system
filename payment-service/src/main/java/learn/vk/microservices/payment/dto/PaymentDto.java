package learn.vk.microservices.payment.dto;

import lombok.Data;

@Data
public class PaymentDto {
    private Long productId;
    private Long quantity;
}
