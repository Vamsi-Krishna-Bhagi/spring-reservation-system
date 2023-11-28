package learn.vk.microservices.payment;

import jakarta.annotation.PostConstruct;
import learn.vk.microservices.payment.entity.Payment;
import learn.vk.microservices.payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PaymentServiceApplication {

	@Autowired
	private PaymentRepository paymentRepository;

	public static void main(String[] args) {
		SpringApplication.run(PaymentServiceApplication.class, args);
	}

	@PostConstruct
	public void init() {
		paymentRepository.save(new Payment(1L, 10L));
		paymentRepository.save(new Payment(2L, 20L));
		paymentRepository.save(new Payment(3L, 30L));
	}
}
