package learn.vk.microservices.reservation.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import learn.vk.microservices.reservation.dto.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    private Long customerId;
    private Long hotelId;
    private Long paymentId;
    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(jakarta.persistence.EnumType.STRING)
    private ReservationStatus status;
}
