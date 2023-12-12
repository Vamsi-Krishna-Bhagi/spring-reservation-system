package learn.vk.microservices.reservation.service;

import learn.vk.microservices.reservation.dto.*;
import learn.vk.microservices.reservation.entity.Reservation;
import learn.vk.microservices.reservation.exception.NotFoundException;
import learn.vk.microservices.reservation.repository.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static learn.vk.microservices.reservation.dto.Constants.*;

@Service
@Slf4j
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CustomerService customerService;
    private final HotelsService hotelsService;
    private final PaymentsService paymentsService;

    private final KafkaTemplate<String, NotificationDto> kafkaTemplate;

    public ReservationService(ReservationRepository reservationRepository, CustomerService customerService,
                              HotelsService hotelsService, PaymentsService paymentsService, KafkaTemplate<String, NotificationDto> kafkaTemplate) {
        this.reservationRepository = reservationRepository;
        this.customerService = customerService;
        this.hotelsService = hotelsService;
        this.paymentsService = paymentsService;
        this.kafkaTemplate = kafkaTemplate;
    }

    public ReservationDto getReservationById(Long productId) {
        Reservation reservation = reservationRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Reservation not found"));

        ReservationDto reservationDto = new ReservationDto();
        BeanUtils.copyProperties(reservation, reservationDto);

        return reservationDto;
    }

    public ReservationDto makeReservation(ReservationDto reservationDto) {

        CustomerDto customerDto = customerService.getCustomerById(reservationDto.getCustomerId());
        log.info("Customer found: " + customerDto.getName());

        HotelDto hotelDto = hotelsService.getHotelById(reservationDto.getHotelId());
        log.info("Hotel found: " + hotelDto.getName());

        reservationDto.setStatus(ReservationStatus.CREATED);
        Reservation reservation = new Reservation();
        BeanUtils.copyProperties(reservationDto, reservation);
        reservation = reservationRepository.save(reservation);
        log.info("Reservation created: " + reservation.getId());
        reservationDto.setId(reservation.getId());

        PaymentDto paymentDto = null;
        try {
            paymentDto = paymentsService.makePayment(reservationDto, hotelDto, reservation);
        } catch (Exception e) {
            NotificationDto notificationDto = getNotificationDto(reservationDto, customerDto, hotelDto);
            kafkaTemplate.send(PAYMENT_FAILED, notificationDto);
            throw e;
        } finally {
            reservationRepository.save(reservation);
        }

        try {
            hotelsService.handleHotels(reservationDto, hotelDto, reservation);
        } catch (Exception e) {
            paymentsService.reversePayment(paymentDto);

            NotificationDto notificationDto = getNotificationDto(reservationDto, customerDto, hotelDto);
            kafkaTemplate.send(RESERVATION_FAILED, notificationDto);

            throw e;
        } finally {
            reservationRepository.save(reservation);
        }

        log.info("Reservation updated. id= {}, status= {}", reservation.getId(), reservation.getStatus());

        NotificationDto notificationDto = getNotificationDto(reservationDto, customerDto, hotelDto);

        kafkaTemplate.send(RESERVATION_CONFIRMED, notificationDto);
        log.info("Notification sent to customer: " + notificationDto.getCustomerEmail());

        return reservationDto;
    }


    private static NotificationDto getNotificationDto(ReservationDto reservationDto, CustomerDto customerDto, HotelDto hotelDto) {
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setCustomerEmail(customerDto.getEmail());
        notificationDto.setCustomerName(customerDto.getName());
        notificationDto.setHotelName(hotelDto.getName());
        notificationDto.setPricePerNight(hotelDto.getPricePerNight());
        notificationDto.setStartDate(reservationDto.getStartDate());
        notificationDto.setEndDate(reservationDto.getEndDate());
        notificationDto.setStatus(reservationDto.getStatus());
        return notificationDto;
    }
}
