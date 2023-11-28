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
    public HotelDto getInventory(@PathVariable Long id) {
        return hotelService.getInventoryByProductId(id);
    }

    @PutMapping
    public HotelDto createInventory(@RequestBody HotelDto hotelDto) {
        return hotelService.updateInventoryItem(hotelDto);
    }

    @PostMapping
    public HotelDto updateInventory(@RequestBody HotelDto hotelDto) {
        return hotelService.createInventoryItem(hotelDto);
    }
}
