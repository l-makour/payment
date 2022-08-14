package com.checkconsulting.payment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
    @Mock
    PaymentRepository paymentRepository;

    @Test
    public void getAllPaymentsTest() {
        Payment payment1 = Payment.builder()
                .id(1)
                .amount(100F)
                .paymentDate(LocalDateTime.now())
                .orderId(12)
                .build();
        Payment payment2 = Payment.builder()
                .id(2)
                .amount(200F)
                .paymentDate(LocalDateTime.now())
                .orderId(121)
                .build();

        List<Payment> payments = new ArrayList<>();
        payments.add(payment1);
        payments.add(payment2);
        Mockito.when(paymentRepository.findAll()).thenReturn(payments);

        PaymentService paymentService = new PaymentService(paymentRepository);
        List<PaymentDto> paymentReturned = paymentService.getAllPayments();
        Assertions.assertEquals(paymentReturned.size(), 2);
        Assertions.assertEquals(paymentReturned.get(0).getAmount(), 100F);
    }

    @Test
    public void ifShouldGetPaymentByIdTest() throws PayementNotFoundException {
        Payment payment1 = Payment.builder()
                .id(1)
                .amount(100F)
                .paymentDate(LocalDateTime.now())
                .orderId(12)
                .build();
        Mockito.when(paymentRepository.findById(1)).thenReturn(Optional.ofNullable(payment1));
        PaymentService paymentService = new PaymentService(paymentRepository);
        PaymentDto paymentReturned = paymentService.getPaymentById(1);
        Assertions.assertNotNull(paymentReturned);
        Assertions.assertEquals(paymentReturned.getAmount(), 100F);

        Mockito.when(paymentRepository.findById(2)).thenReturn(Optional.empty());
        PayementNotFoundException payementNotFoundException = Assertions.assertThrows(PayementNotFoundException.class, () -> {
            paymentService.getPaymentById(2);
        });
        Assertions.assertEquals(payementNotFoundException.getMessage(),"payment with id 2 not found");
    }
}