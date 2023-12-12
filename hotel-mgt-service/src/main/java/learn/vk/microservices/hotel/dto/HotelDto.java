package learn.vk.microservices.hotel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "HotelDto", description = "Hotel details")
public class HotelDto {
    @Schema(description = "Hotel id")
    private Long id;

    @Schema(description = "Hotel available rooms")
    private Long availableRooms;

    @Schema(description = "Hotel name")
    private String name;

    @Schema(description = "Hotel address")
    private Double pricePerNight;
}
