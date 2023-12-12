package learn.vk.microservices.payment.controller;

import learn.vk.microservices.payment.dto.PaymentDto;
import learn.vk.microservices.payment.service.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/{id}")
    public PaymentDto getPaymentById(@PathVariable Long id) {
        return paymentService.getPaymentById(id);
    }


    @PostMapping
    public PaymentDto makePayment(@RequestBody PaymentDto paymentDto) {
        return paymentService.makePayment(paymentDto);
    }

    @DeleteMapping("/{id}")
    public PaymentDto processRefund(@PathVariable Long id) {
       return  paymentService.processRefund(id);
    }
}
