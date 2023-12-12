package learn.vk.microservices.reservation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(name = "ReservationDto", description = "Reservation details")
public class ReservationDto {
    @Schema(description = "Reservation id")
    private Long id;

    @Schema(description = "Customer id")
    private Long customerId;

    @Schema(description = "Hotel id")
    private Long hotelId;

    @Schema(description = "Payment id")
    private Long paymentId;

    @Schema(description = "Reservation start date")
    private LocalDate startDate;

    @Schema(description = "Reservation end date")
    private LocalDate endDate;

    @Schema(description = "Reservation status")
    private ReservationStatus status;

}
