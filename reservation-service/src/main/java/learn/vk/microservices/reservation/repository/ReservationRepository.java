package learn.vk.microservices.reservation.repository;

import learn.vk.microservices.reservation.entity.Reservation;

public interface ReservationRepository extends org.springframework.data.jpa.repository.JpaRepository<Reservation, Long> {
}
