package learn.vk.microservices.customer.controller;

import learn.vk.microservices.customer.dto.CustomerDto;
import learn.vk.microservices.customer.service.CustomerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    public CustomerDto getInventory(@PathVariable Long id) {
        return customerService.getInventoryByProductId(id);
    }

    @PutMapping
    public CustomerDto createInventory(@RequestBody CustomerDto customerDto) {
        return customerService.updateInventoryItem(customerDto);
    }

    @PostMapping
    public CustomerDto updateInventory(@RequestBody CustomerDto customerDto) {
        return customerService.createInventoryItem(customerDto);
    }
}
