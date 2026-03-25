package com.bank.customer.exception;

import java.util.List;

public record ValidationErrorResponse(
        String error,
        int status,
        List<String> list){}
