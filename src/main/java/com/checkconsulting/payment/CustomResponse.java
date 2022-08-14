package com.checkconsulting.payment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomResponse<T> {
    T customDto;
    String errorMessage;
    ResponseStatus responseStatus;
}
