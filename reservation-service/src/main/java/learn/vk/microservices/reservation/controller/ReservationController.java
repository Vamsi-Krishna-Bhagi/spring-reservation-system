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
    public ReservationDto getInventory(@PathVariable Long id) {
        return reservationService.getInventoryByProductId(id);
    }

    @PutMapping
    public ReservationDto createInventory(@RequestBody ReservationDto reservationDto) {
        return reservationService.updateInventoryItem(reservationDto);
    }

    @PostMapping
    public ReservationDto updateInventory(@RequestBody ReservationDto reservationDto) {
        return reservationService.createInventoryItem(reservationDto);
    }
}
