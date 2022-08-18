package com.checkconsulting.payment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

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
        ResponseEntity<CustomResponse<PaymentDto>> customResponse = testRestTemplate.exchange("http://localhost:" + port + "/api/v1/payments/1",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<CustomResponse<PaymentDto>>() {
                });
        Assertions.assertEquals(customResponse.getBody().getCustomDto().getAmount(), 100F);
        Assertions.assertEquals(customResponse.getBody().getResponseStatus(), ResponseStatus.OK);
        Assertions.assertEquals(customResponse.getBody().customDto.getOrderId(), 1);

        CustomResponse<PaymentDto> customResponseNotFound = testRestTemplate.getForObject("http://localhost:" + port + "/api/v1/payments/10", CustomResponse.class);
        Assertions.assertEquals(customResponseNotFound.responseStatus, ResponseStatus.KO);
        Assertions.assertEquals(customResponseNotFound.errorMessage, "payment with id 10 not found");
    }
}