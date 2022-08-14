package com.checkconsulting.payment;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public List<PaymentDto> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(this::paymentToPaymentDto).collect(Collectors.toList());
    }

    public PaymentDto getPaymentById(Integer id) throws PayementNotFoundException {
        Optional<Payment> payment = paymentRepository.findById(id);
        if (payment.isPresent()) {
            return paymentToPaymentDto(payment.get());
        }
        throw new PayementNotFoundException("payment with id " + id + " not found");
    }

    private PaymentDto paymentToPaymentDto(Payment payment) {
        return PaymentDto.builder()
                .paymentDate(payment.getPaymentDate())
                .amount(payment.getAmount())
                .orderId(payment.getOrderId())
                .build();
    }
}
