package learn.vk.microservices.reservation.service;

import com.netflix.discovery.converters.Auto;
import learn.vk.microservices.reservation.client.CustomerClient;
import learn.vk.microservices.reservation.dto.CustomerDto;
import learn.vk.microservices.reservation.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class CustomerService {

    @Autowired
    private CustomerClient customerClient;

    CustomerDto getCustomerById(Long customerId) {
        CustomerDto customerDto = Objects.requireNonNull(customerClient.getCustomerById(customerId));
        if (customerDto.getId() == null) {
            throw new NotFoundException("Customer not found");
        }
        return customerDto;
    }
}
