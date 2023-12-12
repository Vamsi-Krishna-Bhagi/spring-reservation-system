package learn.vk.microservices.reservation.service;

import learn.vk.microservices.reservation.client.HotelClient;
import learn.vk.microservices.reservation.dto.HotelDto;
import learn.vk.microservices.reservation.dto.ReservationDto;
import learn.vk.microservices.reservation.dto.ReservationStatus;
import learn.vk.microservices.reservation.entity.Reservation;
import learn.vk.microservices.reservation.exception.GenericException;
import learn.vk.microservices.reservation.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class HotelsService {
    @Autowired
    private HotelClient hotelClient;

    void bookHotelRoom(ReservationDto reservationDto, HotelDto hotelDto, Reservation reservation) {
        hotelDto.setAvailableRooms(hotelDto.getAvailableRooms() - 1);
        try {
            hotelDto = hotelClient.updateHotel(hotelDto);
            log.info("Hotel updated: " + hotelDto.getId());
        } catch (Exception e) {
            reservation.setStatus(ReservationStatus.RESEVATION_FAILED);
            reservationDto.setStatus(ReservationStatus.RESEVATION_FAILED);

            throw e;
        }
        reservation.setStatus(ReservationStatus.RESERVED);
        reservationDto.setStatus(ReservationStatus.RESERVED);
    }


    void releaseHotelRoom(ReservationDto reservationDto, HotelDto hotelDto, Reservation reservation) {
        hotelDto.setAvailableRooms(hotelDto.getAvailableRooms() + 1);
        try {
            hotelDto = hotelClient.updateHotel(hotelDto);
            log.info("Hotel updated: " + hotelDto.getId());
        } catch (Exception e) {
            reservation.setStatus(ReservationStatus.RESEVATION_FAILED);
            reservationDto.setStatus(ReservationStatus.RESEVATION_FAILED);

            throw e;
        }
        reservation.setStatus(ReservationStatus.CANCELLED);
        reservationDto.setStatus(ReservationStatus.CANCELLED);
    }

    HotelDto getHotelById(Long hotelId) {
        HotelDto hotelDto = Objects.requireNonNull(hotelClient.getHotelById(hotelId));
        if (hotelDto.getId() == null) {
            throw new NotFoundException("Hotel not found");
        } else if (hotelDto.getAvailableRooms() < 1) {
            throw new GenericException("Rooms not available");
        }
        return hotelDto;
    }
}
