package learn.vk.microservices.hotel.controller;

import learn.vk.microservices.hotel.dto.HotelDto;
import learn.vk.microservices.hotel.service.HotelService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class HotelController {
    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/{id}")
    public HotelDto getHotelById(@PathVariable Long id) {
        return hotelService.getHotelById(id);
    }

    @PostMapping
    public HotelDto updateHotel(@RequestBody HotelDto hotelDto) {
        return hotelService.updateHotel(hotelDto);
    }
}
