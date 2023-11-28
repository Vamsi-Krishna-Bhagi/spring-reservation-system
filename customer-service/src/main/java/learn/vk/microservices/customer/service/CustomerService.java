package learn.vk.microservices.customer.service;

import learn.vk.microservices.customer.dto.CustomerDto;
import learn.vk.microservices.customer.entity.Customer;
import learn.vk.microservices.customer.exception.NotFoundException;
import learn.vk.microservices.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerDto getInventoryByProductId(Long productId) {
        Customer customer = customerRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Inventory not found"));

        CustomerDto customerDto = new CustomerDto();
        customerDto.setProductId(customer.getItemId());
        customerDto.setQuantity(customer.getQuantity());
        return customerDto;

    }

    public CustomerDto updateInventoryItem(CustomerDto customerDto) {
        Customer customer = customerRepository.findById(customerDto.getProductId())
                .orElseThrow(() -> new NotFoundException("Inventory not found"));
        customer.setQuantity(customerDto.getQuantity());

        customer = customerRepository.save(customer);
        customerDto.setQuantity(customer.getQuantity());
        return customerDto;
    }

    public CustomerDto createInventoryItem(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setItemId(customerDto.getProductId());
        customer.setQuantity(customerDto.getQuantity());

        customer = customerRepository.save(customer);

        customerDto.setProductId(customer.getItemId());
        return customerDto;
    }
}
