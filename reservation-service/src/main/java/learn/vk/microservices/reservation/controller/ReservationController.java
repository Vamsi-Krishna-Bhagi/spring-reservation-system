package learn.vk.microservices.reservation.controller;

import learn.vk.microservices.reservation.dto.ReservationDto;
import learn.vk.microservices.reservation.service.ReservationCancelService;
import learn.vk.microservices.reservation.service.ReservationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    private final ReservationService reservationService;
    private final ReservationCancelService reservationCancelService;

    public ReservationController(ReservationService reservationService, ReservationCancelService reservationCancelService) {
        this.reservationService = reservationService;
        this.reservationCancelService = reservationCancelService;
    }

    @GetMapping("/{id}")
    public ReservationDto getReservationById(@PathVariable Long id) {
        return reservationService.getReservationById(id);
    }

    @PostMapping
    public ReservationDto makeReservation(@RequestBody ReservationDto reservationDto) {
        return reservationService.makeReservation(reservationDto);
    }

    @DeleteMapping("/{id}")
    public void cancelReservation(@PathVariable Long id) {
        reservationCancelService.cancelReservation(id);
    }
}
