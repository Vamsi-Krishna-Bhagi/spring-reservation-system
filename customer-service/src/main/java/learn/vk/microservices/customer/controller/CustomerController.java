package learn.vk.microservices.customer.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.models.annotations.OpenAPI30;
import learn.vk.microservices.customer.dto.CustomerDto;
import learn.vk.microservices.customer.service.CustomerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@OpenAPIDefinition(info = @io.swagger.v3.oas.annotations.info.Info(title = "Customer Api"), tags = @io.swagger.v3.oas.annotations.tags.Tag(name = "Customer Api"))
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get customer by id")
    @ApiResponse(responseCode = "200", description = "Customer found")
    @ApiResponse(responseCode = "404", description = "Customer not found")
    @Parameter(name = "id", description = "customer id")
    public CustomerDto getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping
    @Operation(summary = "Register customer")
    @ApiResponse(responseCode = "200", description = "Customer registered")
    @ApiResponse(responseCode = "400", description = "Customer not registered")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Customer details", required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(schema = @Schema(implementation = CustomerDto.class)))
    public CustomerDto register(@RequestBody CustomerDto customerDto) {
        return customerService.createCustomer(customerDto);
    }
}
