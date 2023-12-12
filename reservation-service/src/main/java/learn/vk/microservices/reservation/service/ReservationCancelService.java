package learn.vk.microservices.reservation.service;

import learn.vk.microservices.reservation.dto.*;
import learn.vk.microservices.reservation.entity.Reservation;
import learn.vk.microservices.reservation.exception.NotFoundException;
import learn.vk.microservices.reservation.repository.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static learn.vk.microservices.reservation.dto.Constants.REFUND_CONFIRMED;
import static learn.vk.microservices.reservation.dto.Constants.REFUND_FAILED;
import static learn.vk.microservices.reservation.util.NotificationUtil.getNotificationDto;

@Service
@Slf4j
public class ReservationCancelService {

    private final ReservationRepository reservationRepository;
    private final CustomerService customerService;
    private final HotelsService hotelsService;
    private final PaymentsService paymentsService;

    private final KafkaTemplate<String, NotificationDto> kafkaTemplate;

    public ReservationCancelService(ReservationRepository reservationRepository, CustomerService customerService,
                              HotelsService hotelsService, PaymentsService paymentsService, KafkaTemplate<String, NotificationDto> kafkaTemplate) {
        this.reservationRepository = reservationRepository;
        this.customerService = customerService;
        this.hotelsService = hotelsService;
        this.paymentsService = paymentsService;
        this.kafkaTemplate = kafkaTemplate;
    }

    public ReservationDto cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException("Reservation not found"));

        CustomerDto customerDto = customerService.getCustomerById(reservation.getCustomerId());
        HotelDto hotelDto = hotelsService.getHotelById(reservation.getHotelId());
        ReservationDto reservationDto = new ReservationDto();
        BeanUtils.copyProperties(reservation, reservationDto);

//        Payment Refund
        //Check if reservation is in future
        if (reservation.getStartDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Cannot cancel reservation in past");
        }
        PaymentDto paymentDto;
        try {
            paymentDto = paymentsService.processRefund(reservation);

        } catch (Exception e) {

            NotificationDto notificationDto = getNotificationDto(reservationDto, customerDto, hotelDto);
            kafkaTemplate.send(REFUND_FAILED, notificationDto);
            log.error("Error while processing refund for reservation: " + reservationId);
            throw e;
        } finally {
            reservationRepository.save(reservation);
        }

        try {
            hotelsService.releaseHotelRoom(reservationDto, hotelDto, reservation);
        } catch (Exception e) {
            paymentsService.reversePayment(paymentDto);

            NotificationDto notificationDto = getNotificationDto(reservationDto, customerDto, hotelDto);
            kafkaTemplate.send(REFUND_FAILED, notificationDto);

            throw e;
        } finally {
            reservationRepository.save(reservation);
        }

        log.info("Reservation cancelled. id= {}, status= {}", reservation.getId(), reservation.getStatus());

        NotificationDto notificationDto = getNotificationDto(reservationDto, customerDto, hotelDto);

        kafkaTemplate.send(REFUND_CONFIRMED, notificationDto);
        log.info("Notification sent to customer: " + notificationDto.getCustomerEmail());

        return reservationDto;

    }
}
