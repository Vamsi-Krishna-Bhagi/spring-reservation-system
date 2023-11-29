package learn.vk.microservices.payment.service;

import learn.vk.microservices.payment.dto.PaymentDto;
import learn.vk.microservices.payment.entity.Payment;
import learn.vk.microservices.payment.exception.NotFoundException;
import learn.vk.microservices.payment.repository.PaymentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public PaymentDto getPaymentById(Long productId) {
        Payment payment = paymentRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Payment not found"));

        PaymentDto paymentDto = new PaymentDto();
        BeanUtils.copyProperties(payment, paymentDto);
        return paymentDto;

    }

    public PaymentDto makePayment(PaymentDto paymentDto) {
        Payment payment = new Payment();
        BeanUtils.copyProperties(paymentDto, payment);

        payment = paymentRepository.save(payment);
        paymentDto.setId(payment.getId());
        return paymentDto;
    }
}
