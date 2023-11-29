package learn.vk.microservices.customer.service;

import learn.vk.microservices.customer.dto.CustomerDto;
import learn.vk.microservices.customer.entity.Customer;
import learn.vk.microservices.customer.exception.NotFoundException;
import learn.vk.microservices.customer.repository.CustomerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

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

        customerDto.setId(customer.getId());
        customerDto.setPassword(null);
        return customerDto;
    }
}
