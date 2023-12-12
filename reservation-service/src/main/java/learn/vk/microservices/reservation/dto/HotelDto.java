package learn.vk.microservices.reservation.dto;

import lombok.Data;

@Data
public class HotelDto {
    private Long id;
    private Long availableRooms;
    private String name;
    private Double pricePerNight;
}
