package com.checkconsulting.payment;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class PaymentDto {
    private Float amount;
    private Integer orderId;
    private LocalDateTime paymentDate;
}
