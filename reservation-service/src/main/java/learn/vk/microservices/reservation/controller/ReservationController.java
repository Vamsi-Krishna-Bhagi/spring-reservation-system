package learn.vk.microservices.reservation.controller;

import learn.vk.microservices.reservation.dto.ReservationDto;
import learn.vk.microservices.reservation.service.ReservationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/{id}")
    public ReservationDto getReservationById(@PathVariable Long id) {
        return reservationService.getReservationById(id);
    }

    @PostMapping
    public ReservationDto makeReservation(@RequestBody ReservationDto reservationDto) {
        return reservationService.makeReservation(reservationDto);
    }
}
