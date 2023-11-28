package learn.vk.microservices.payment.service;

import learn.vk.microservices.payment.dto.PaymentDto;
import learn.vk.microservices.payment.entity.Payment;
import learn.vk.microservices.payment.exception.NotFoundException;
import learn.vk.microservices.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public PaymentDto getInventoryByProductId(Long productId) {
        Payment payment = paymentRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Inventory not found"));

        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setProductId(payment.getItemId());
        paymentDto.setQuantity(payment.getQuantity());
        return paymentDto;

    }

    public PaymentDto updateInventoryItem(PaymentDto paymentDto) {
        Payment payment = paymentRepository.findById(paymentDto.getProductId())
                .orElseThrow(() -> new NotFoundException("Inventory not found"));
        payment.setQuantity(paymentDto.getQuantity());

        payment = paymentRepository.save(payment);
        paymentDto.setQuantity(payment.getQuantity());
        return paymentDto;
    }

    public PaymentDto createInventoryItem(PaymentDto paymentDto) {
        Payment payment = new Payment();
        payment.setItemId(paymentDto.getProductId());
        payment.setQuantity(paymentDto.getQuantity());

        payment = paymentRepository.save(payment);

        paymentDto.setProductId(payment.getItemId());
        return paymentDto;
    }
}
