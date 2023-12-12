package learn.vk.microservices.reservation.service;

import learn.vk.microservices.reservation.client.CustomerClient;
import learn.vk.microservices.reservation.client.HotelClient;
import learn.vk.microservices.reservation.client.PaymentClient;
import learn.vk.microservices.reservation.dto.*;
import learn.vk.microservices.reservation.entity.Reservation;
import learn.vk.microservices.reservation.exception.GenericException;
import learn.vk.microservices.reservation.exception.NotFoundException;
import learn.vk.microservices.reservation.repository.ReservationRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final CustomerClient customerClient;
    private final HotelClient hotelClient;
    private final PaymentClient paymentClient;

    public ReservationService(ReservationRepository reservationRepository, CustomerClient customerClient, HotelClient hotelClient, PaymentClient paymentClient) {
        this.reservationRepository = reservationRepository;
        this.customerClient = customerClient;
        this.hotelClient = hotelClient;
        this.paymentClient = paymentClient;
    }

    public ReservationDto getReservationById(Long productId) {
        Reservation reservation = reservationRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Reservation not found"));

        ReservationDto reservationDto = new ReservationDto();
        BeanUtils.copyProperties(reservation, reservationDto);

        return reservationDto;
    }

    public ReservationDto makeReservation(ReservationDto reservationDto) {
        CustomerDto customerDto = Objects.requireNonNull(customerClient.getCustomerById(reservationDto.getCustomerId()));
        if (customerDto.getId() == null) {
            throw new NotFoundException("Customer not found");
        }

        HotelDto hotelDto = Objects.requireNonNull(hotelClient.getHotelById(reservationDto.getHotelId()));
        if (hotelDto.getId() == null) {
            throw new NotFoundException("Hotel not found");
        } else if (hotelDto.getAvailableRooms() < 1) {
            throw new GenericException("Rooms not available");
        }

        reservationDto.setStatus(ReservationStatus.CREATED);
        Reservation reservation = new Reservation();
        BeanUtils.copyProperties(reservationDto, reservation);
        reservation = reservationRepository.save(reservation);
        reservationDto.setId(reservation.getId());

        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setAmount(hotelDto.getPricePerNight());
        paymentDto.setReservationId(reservation.getId());

        paymentDto = Objects.requireNonNull(paymentClient.makePayment(paymentDto));
        if (paymentDto.getId() == null) {
            throw new GenericException("Payment failed");
        }
//        TODO: Add saga pattern
        reservation.setStatus(ReservationStatus.PAID);
        reservationRepository.save(reservation);


        hotelDto.setAvailableRooms(hotelDto.getAvailableRooms() - 1);
        hotelClient.updateHotel(hotelDto);
//        TODO: Add saga pattern
        reservation.setStatus(ReservationStatus.RESERVED);
        reservationRepository.save(reservation);

        return reservationDto;
    }
}
