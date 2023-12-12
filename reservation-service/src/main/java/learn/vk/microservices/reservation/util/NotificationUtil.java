package learn.vk.microservices.reservation.util;

import learn.vk.microservices.reservation.dto.CustomerDto;
import learn.vk.microservices.reservation.dto.HotelDto;
import learn.vk.microservices.reservation.dto.NotificationDto;
import learn.vk.microservices.reservation.dto.ReservationDto;

public class NotificationUtil {
    public static NotificationDto getNotificationDto(ReservationDto reservationDto, CustomerDto customerDto, HotelDto hotelDto) {
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
