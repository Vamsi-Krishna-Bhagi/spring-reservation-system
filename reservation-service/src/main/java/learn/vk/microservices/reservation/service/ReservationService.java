package learn.vk.microservices.reservation.service;

import learn.vk.microservices.reservation.dto.ReservationDto;
import learn.vk.microservices.reservation.entity.Reservation;
import learn.vk.microservices.reservation.exception.NotFoundException;
import learn.vk.microservices.reservation.repository.ReservationRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public ReservationDto getReservationById(Long productId) {
        Reservation reservation = reservationRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Reservation not found"));

        ReservationDto reservationDto = new ReservationDto();
        BeanUtils.copyProperties(reservation, reservationDto);

        return reservationDto;
    }

    public ReservationDto makeReservation(ReservationDto reservationDto) {
        Reservation reservation = new Reservation();
        BeanUtils.copyProperties(reservationDto, reservation);

        reservation = reservationRepository.save(reservation);
        reservationDto.setId(reservation.getId());
        return reservationDto;
    }
}
