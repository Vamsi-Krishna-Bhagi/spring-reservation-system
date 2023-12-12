package learn.vk.microservices.reservation.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NotificationDto {
    private String customerName;
    private String customerEmail;
    private String hotelName;
    private Double pricePerNight;
    private LocalDate startDate;
    private LocalDate endDate;
    private ReservationStatus status;

}
