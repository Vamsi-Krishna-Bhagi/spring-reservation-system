package learn.vk.microservices.customer.dto;

import lombok.Data;

@Data
public class CustomerDto {
    private Long productId;
    private Long quantity;
}
