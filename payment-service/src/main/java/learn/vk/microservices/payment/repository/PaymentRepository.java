package learn.vk.microservices.payment.repository;

import learn.vk.microservices.payment.entity.Payment;

public interface PaymentRepository extends org.springframework.data.jpa.repository.JpaRepository<Payment, Long> {
}
