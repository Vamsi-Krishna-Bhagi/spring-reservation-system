package learn.vk.microservices.customer.controller;

import learn.vk.microservices.customer.dto.CustomerDto;
import learn.vk.microservices.customer.service.CustomerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    public CustomerDto getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping
    public CustomerDto register(@RequestBody CustomerDto customerDto) {
        return customerService.createCustomer(customerDto);
    }
}
