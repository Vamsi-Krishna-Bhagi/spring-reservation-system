package learn.vk.microservices.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationNotificationDto {
    private String customerName;
    private String customerEmail;
    private String hotelName;
    private Double pricePerNight;
    private LocalDate startDate;
    private LocalDate endDate;
    private ReservationStatus status;

}
