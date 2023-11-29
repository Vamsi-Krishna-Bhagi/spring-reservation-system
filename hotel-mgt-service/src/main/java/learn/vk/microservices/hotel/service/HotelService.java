package learn.vk.microservices.hotel.service;

import learn.vk.microservices.hotel.dto.HotelDto;
import learn.vk.microservices.hotel.entity.Hotel;
import learn.vk.microservices.hotel.exception.NotFoundException;
import learn.vk.microservices.hotel.repository.HotelRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class HotelService {
    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public HotelDto getHotelById(Long productId) {
        Hotel hotel = hotelRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Hotel not found"));

        HotelDto hotelDto = new HotelDto();
        BeanUtils.copyProperties(hotel,hotelDto);
        return hotelDto;

    }

    public HotelDto updateHotel(HotelDto hotelDto) {
        Hotel hotel = hotelRepository.findById(hotelDto.getId())
                .orElseThrow(() -> new NotFoundException("Hotel not found"));
        hotel.setAvailableRooms(hotelDto.getAvailableRooms());
        hotel.setName(hotelDto.getName());

        hotel = hotelRepository.save(hotel);
        BeanUtils.copyProperties(hotel,hotelDto);
        return hotelDto;
    }

}
