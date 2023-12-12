package learn.vk.microservices.reservation.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservationDto {
    private Long id;
    private Long customerId;
    private Long hotelId;
    private LocalDate startDate;
    private LocalDate endDate;
    private ReservationStatus status;

}
