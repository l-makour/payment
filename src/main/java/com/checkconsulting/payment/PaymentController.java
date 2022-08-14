package com.checkconsulting.payment;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
                paymentService.getAllPayments()));
    }

    @GetMapping("payments/{id}")
    public ResponseEntity<CustomResponse<PaymentDto>> getPayment(@PathVariable(name = "id") Integer id) {
        PaymentDto paymentDto;
        CustomResponse<PaymentDto> customResponse;
        try {
            paymentDto = paymentService.getPaymentById(id);
            customResponse = CustomResponse.<PaymentDto>builder()
                    .responseStatus(ResponseStatus.OK)
                    .errorMessage("")
                    .customDto(paymentDto)
                    .build();
        } catch (PayementNotFoundException e) {
            customResponse = CustomResponse.<PaymentDto>builder()
                    .responseStatus(ResponseStatus.KO)
                    .errorMessage(e.getMessage())
                    .build();
        }
        return ResponseEntity.ok(customResponse);
    }
}
