package learn.vk.microservices.notification.dto;

import lombok.Data;

@Data
public class HotelDto {
    private Long id;
    private Long availableRooms;
    private String name;
    private Double pricePerNight;
}
