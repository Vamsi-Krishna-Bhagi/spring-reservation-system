package learn.vk.microservices.hotel.repository;

import learn.vk.microservices.hotel.entity.Hotel;

public interface HotelRepository extends org.springframework.data.jpa.repository.JpaRepository<Hotel, Long> {
}
