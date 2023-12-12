package learn.vk.microservices.reservation.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import learn.vk.microservices.reservation.dto.ReservationDto;
import learn.vk.microservices.reservation.service.ReservationCancelService;
import learn.vk.microservices.reservation.service.ReservationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
@OpenAPIDefinition(info = @io.swagger.v3.oas.annotations.info.Info(title = "Reservation Api"))
public class ReservationController {
    private final ReservationService reservationService;
    private final ReservationCancelService reservationCancelService;

    public ReservationController(ReservationService reservationService, ReservationCancelService reservationCancelService) {
        this.reservationService = reservationService;
        this.reservationCancelService = reservationCancelService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get reservation by id")
    @Parameter(name = "id", description = "reservation id")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Reservation found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Reservation not found")
    })
    public ReservationDto getReservationById(@PathVariable Long id) {
        return reservationService.getReservationById(id);
    }

    @PostMapping
    @Operation(summary = "Make reservation")
    @Parameter(name = "reservationDto", description = "reservation details")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Reservation successful"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Reservation failed")
    })
    public ReservationDto makeReservation(@RequestBody ReservationDto reservationDto) {
        return reservationService.makeReservation(reservationDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancel reservation")
    @Parameter(name = "id", description = "reservation id")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Reservation cancelled"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Reservation cancellation failed")
    })
    public void cancelReservation(@PathVariable Long id) {
        reservationCancelService.cancelReservation(id);
    }
}
