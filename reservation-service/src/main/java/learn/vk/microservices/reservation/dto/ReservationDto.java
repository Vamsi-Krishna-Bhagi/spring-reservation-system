package learn.vk.microservices.reservation.dto;

import lombok.Data;

@Data
public class ReservationDto {
    private Long productId;
    private Long quantity;
}
