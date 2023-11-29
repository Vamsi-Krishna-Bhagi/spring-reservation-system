package learn.vk.microservices.customer;

import jakarta.annotation.PostConstruct;
import learn.vk.microservices.customer.entity.Customer;
import learn.vk.microservices.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CustomerServiceApplication {

    @Autowired
    private CustomerRepository customerRepository;

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @PostConstruct
    public void init() {
        customerRepository.save(new Customer(1L, "Vijay Kumar", "vijaykumar@gmail.com", "1234567890"));
        customerRepository.save(new Customer(2L, "Rajesh Kumar", "rajeshkumar@gmail.com", "1234567890"));
        customerRepository.save(new Customer(3L, "Ramesh Kumar", "ramesh@yahoo.com", "1234567890"));
    }
}
