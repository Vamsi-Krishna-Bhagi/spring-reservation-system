package learn.vk.microservices.reservation.service;

import learn.vk.microservices.reservation.dto.ReservationDto;
import learn.vk.microservices.reservation.entity.Reservation;
import learn.vk.microservices.reservation.exception.NotFoundException;
import learn.vk.microservices.reservation.repository.ReservationRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public ReservationDto getInventoryByProductId(Long productId) {
        Reservation reservation = reservationRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Inventory not found"));

        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setProductId(reservation.getItemId());
        reservationDto.setQuantity(reservation.getQuantity());
        return reservationDto;

    }

    public ReservationDto updateInventoryItem(ReservationDto reservationDto) {
        Reservation reservation = reservationRepository.findById(reservationDto.getProductId())
                .orElseThrow(() -> new NotFoundException("Inventory not found"));
        reservation.setQuantity(reservationDto.getQuantity());

        reservation = reservationRepository.save(reservation);
        reservationDto.setQuantity(reservation.getQuantity());
        return reservationDto;
    }

    public ReservationDto createInventoryItem(ReservationDto reservationDto) {
        Reservation reservation = new Reservation();
        reservation.setItemId(reservationDto.getProductId());
        reservation.setQuantity(reservationDto.getQuantity());

        reservation = reservationRepository.save(reservation);

        reservationDto.setProductId(reservation.getItemId());
        return reservationDto;
    }
}
