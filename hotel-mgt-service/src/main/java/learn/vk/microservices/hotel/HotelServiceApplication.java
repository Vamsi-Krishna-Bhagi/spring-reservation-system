package learn.vk.microservices.hotel;

import jakarta.annotation.PostConstruct;
import learn.vk.microservices.hotel.entity.Hotel;
import learn.vk.microservices.hotel.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HotelServiceApplication {

	@Autowired
	private HotelRepository hotelRepository;

	public static void main(String[] args) {
		SpringApplication.run(HotelServiceApplication.class, args);
	}

	@PostConstruct
	public void init() {
		hotelRepository.save(new Hotel(1L, 10L));
		hotelRepository.save(new Hotel(2L, 20L));
		hotelRepository.save(new Hotel(3L, 30L));
	}
}
