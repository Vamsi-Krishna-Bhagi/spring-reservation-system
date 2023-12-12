package learn.vk.microservices.reservation.client;

import learn.vk.microservices.reservation.dto.PaymentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service", path = "/api/payments")
public interface PaymentClient {

    @PostMapping
    public PaymentDto makePayment(@RequestBody PaymentDto paymentDto);
}
