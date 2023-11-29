package learn.vk.microservices.hotel.dto;

import lombok.Data;

@Data
public class HotelDto {
    private Long id;
    private Long availableRooms;
    private String name;
}
