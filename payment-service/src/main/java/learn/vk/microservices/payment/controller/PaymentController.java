package learn.vk.microservices.payment.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import learn.vk.microservices.payment.dto.PaymentDto;
import learn.vk.microservices.payment.service.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@OpenAPIDefinition(info = @io.swagger.v3.oas.annotations.info.Info(title = "Payment Api"))
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get payment by id")
    @Parameter(name = "id", description = "payment id")
    @ApiResponse(responseCode = "200", description = "Payment found")
    @ApiResponse(responseCode = "404", description = "Payment not found")
    public PaymentDto getPaymentById(@PathVariable Long id) {
        return paymentService.getPaymentById(id);
    }


    @PostMapping
    @Parameter(name = "paymentDto", description = "payment details")
    @Operation(summary = "Make payment")
    @ApiResponse(responseCode = "200", description = "Payment successful")
    @ApiResponse(responseCode = "400", description = "Payment failed")
    public PaymentDto makePayment(@RequestBody PaymentDto paymentDto) {
        return paymentService.makePayment(paymentDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Process refund")
    @Parameter(name = "id", description = "payment id")
    @ApiResponse(responseCode = "200", description = "Refund successful")
    @ApiResponse(responseCode = "400", description = "Refund failed")
    public PaymentDto processRefund(@PathVariable Long id) {
       return  paymentService.processRefund(id);
    }
}
