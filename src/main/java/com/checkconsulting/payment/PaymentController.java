package com.checkconsulting.payment;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("payments")
    public ResponseEntity<List<PaymentDto>> getPayments() {
        return ResponseEntity.ok((
                paymentService.getAllPayments().stream()
                        .map(this::paymentToPaymentDto).collect(Collectors.toList())));
    }

    private PaymentDto paymentToPaymentDto(Payment payment) {
        return PaymentDto.builder()
                .paymentDate(payment.getPaymentDate())
                .amount(payment.getAmount())
                .orderId(payment.getOrderId())
                .build();
    }
}
