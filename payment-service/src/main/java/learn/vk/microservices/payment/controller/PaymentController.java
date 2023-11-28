package learn.vk.microservices.payment.controller;

import learn.vk.microservices.payment.dto.PaymentDto;
import learn.vk.microservices.payment.service.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/{id}")
    public PaymentDto getInventory(@PathVariable Long id) {
        return paymentService.getInventoryByProductId(id);
    }

    @PutMapping
    public PaymentDto createInventory(@RequestBody PaymentDto paymentDto) {
        return paymentService.updateInventoryItem(paymentDto);
    }

    @PostMapping
    public PaymentDto updateInventory(@RequestBody PaymentDto paymentDto) {
        return paymentService.createInventoryItem(paymentDto);
    }
}
