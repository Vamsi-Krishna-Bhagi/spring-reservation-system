package learn.vk.microservices.customer.service;

import learn.vk.microservices.customer.dto.CustomerDto;
import learn.vk.microservices.customer.entity.Customer;
import learn.vk.microservices.customer.exception.NotFoundException;
import learn.vk.microservices.customer.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    KafkaTemplate<String, CustomerDto> kafkaTemplate;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerDto getCustomerById(Long productId) {
        Customer customer = customerRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Customer not found"));

        CustomerDto customerDto = new CustomerDto();
        BeanUtils.copyProperties(customer, customerDto, "password");

        return customerDto;
    }

    public CustomerDto createCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDto, customer );

        customer = customerRepository.save(customer);
        log.info("Customer created: " + customer.getId());

        customerDto.setId(customer.getId());
        customerDto.setPassword(null);

        kafkaTemplate.send("CustomerCreated", customerDto);
        return customerDto;
    }
}
