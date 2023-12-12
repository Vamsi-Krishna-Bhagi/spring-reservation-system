package learn.vk.microservices.reservation.service;

import learn.vk.microservices.reservation.client.PaymentClient;
import learn.vk.microservices.reservation.dto.HotelDto;
import learn.vk.microservices.reservation.dto.PaymentDto;
import learn.vk.microservices.reservation.dto.ReservationDto;
import learn.vk.microservices.reservation.dto.ReservationStatus;
import learn.vk.microservices.reservation.entity.Reservation;
import learn.vk.microservices.reservation.exception.GenericException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class PaymentsService {

    private final PaymentClient paymentClient;

    public PaymentsService(PaymentClient paymentClient) {
        this.paymentClient = paymentClient;
    }

    public PaymentDto makePayment(ReservationDto reservationDto, HotelDto hotelDto, Reservation reservation) {
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setAmount(hotelDto.getPricePerNight());
        paymentDto.setReservationId(reservation.getId());

        try {
            paymentDto = Objects.requireNonNull(paymentClient.makePayment(paymentDto));
            if (paymentDto.getId() == null) {
                throw new GenericException("Payment failed");
            }
        } catch (Exception e) {
            reservation.setStatus(ReservationStatus.PAYMENT_FAILED);
            reservationDto.setStatus(ReservationStatus.PAYMENT_FAILED);
            throw e;
        }
        reservation.setPaymentId(paymentDto.getId());
        reservationDto.setPaymentId(paymentDto.getId());
        reservation.setStatus(ReservationStatus.PAID);
        reservationDto.setStatus(ReservationStatus.PAID);

        log.info("Payment successful: " + paymentDto.getId());

        return paymentDto;
    }

    public PaymentDto reversePayment( PaymentDto paymentDto) {
        PaymentDto reversePayment = new PaymentDto();
        reversePayment.setAmount(paymentDto.getAmount() * -1);
        reversePayment.setReservationId(paymentDto.getReservationId());

        paymentDto = Objects.requireNonNull(paymentClient.makePayment(paymentDto));
        if (paymentDto.getId() == null) {
            throw new GenericException("Payment failed");
        }

        log.info("Payment successful: " + paymentDto.getId());

        return reversePayment;
    }

    public PaymentDto processRefund(Reservation reservation) {

        PaymentDto paymentDto;
        try {
            paymentDto = Objects.requireNonNull(paymentClient.processRefund(reservation.getPaymentId()));
            if (paymentDto.getId() == null) {
                throw new GenericException("Refund failed");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            reservation.setStatus(ReservationStatus.REFUND_FAILED);
            throw e;
        }

        reservation.setPaymentId(paymentDto.getId());
        log.info("Refund successful: " + paymentDto.getId());

        return paymentDto;
    }
}
