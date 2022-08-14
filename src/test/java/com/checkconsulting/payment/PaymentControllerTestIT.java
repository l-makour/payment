package com.checkconsulting.payment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(classes = PaymentApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PaymentControllerTestIT {
    @LocalServerPort
    Integer port;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    public void getPaymentsTest() {
        PaymentDto[] paymentDtos = testRestTemplate.getForObject("http://localhost:" + port + "/api/v1/payments", PaymentDto[].class);
        Assertions.assertEquals(paymentDtos.length, 2);
        Assertions.assertEquals(paymentDtos[0].getAmount(), 100F);
    }

    @Test
    public void getPaymentTest() {
        CustomResponse<PaymentDto> customResponse = testRestTemplate.getForObject("http://localhost:" + port + "/api/v1/payments/1", CustomResponse.class);
        Assertions.assertEquals(customResponse.getCustomDto().getAmount(), 100F);
        Assertions.assertEquals(customResponse.getCustomDto().getOrderId(), 1);
    }
}