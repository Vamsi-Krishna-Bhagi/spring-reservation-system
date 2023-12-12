package learn.vk.microservices.reservation.client;

import learn.vk.microservices.reservation.dto.HotelDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "hotel-mgt-service", path = "/api/inventory")
public interface HotelClient {
    @GetMapping("/{id}")
    public HotelDto getHotelById(@PathVariable Long id);

    @PostMapping
    public HotelDto updateHotel(@RequestBody HotelDto hotelDto);
}
