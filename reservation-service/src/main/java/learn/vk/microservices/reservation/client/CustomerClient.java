package learn.vk.microservices.reservation.client;

import learn.vk.microservices.reservation.dto.CustomerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service", path = "/api/customers")
public interface CustomerClient {

    @GetMapping("/{id}")
    public CustomerDto getCustomerById(@PathVariable Long id);
}
