package learn.vk.microservices.hotel.service;

import learn.vk.microservices.hotel.dto.HotelDto;
import learn.vk.microservices.hotel.entity.Hotel;
import learn.vk.microservices.hotel.exception.NotFoundException;
import learn.vk.microservices.hotel.repository.HotelRepository;
import org.springframework.stereotype.Service;

@Service
public class HotelService {
    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public HotelDto getInventoryByProductId(Long productId) {
        Hotel hotel = hotelRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Inventory not found"));

        HotelDto hotelDto = new HotelDto();
        hotelDto.setProductId(hotel.getItemId());
        hotelDto.setQuantity(hotel.getQuantity());
        return hotelDto;

    }

    public HotelDto updateInventoryItem(HotelDto hotelDto) {
        Hotel hotel = hotelRepository.findById(hotelDto.getProductId())
                .orElseThrow(() -> new NotFoundException("Inventory not found"));
        hotel.setQuantity(hotelDto.getQuantity());

        hotel = hotelRepository.save(hotel);
        hotelDto.setQuantity(hotel.getQuantity());
        return hotelDto;
    }

    public HotelDto createInventoryItem(HotelDto hotelDto) {
        Hotel hotel = new Hotel();
        hotel.setItemId(hotelDto.getProductId());
        hotel.setQuantity(hotelDto.getQuantity());

        hotel = hotelRepository.save(hotel);

        hotelDto.setProductId(hotel.getItemId());
        return hotelDto;
    }
}
