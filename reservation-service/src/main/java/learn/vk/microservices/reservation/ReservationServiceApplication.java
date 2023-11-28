package learn.vk.microservices.reservation;

import jakarta.annotation.PostConstruct;
import learn.vk.microservices.reservation.entity.Reservation;
import learn.vk.microservices.reservation.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReservationServiceApplication {

	@Autowired
	private ReservationRepository reservationRepository;

	public static void main(String[] args) {
		SpringApplication.run(ReservationServiceApplication.class, args);
	}

	@PostConstruct
	public void init() {
		reservationRepository.save(new Reservation(1L, 10L));
		reservationRepository.save(new Reservation(2L, 20L));
		reservationRepository.save(new Reservation(3L, 30L));
	}
}
