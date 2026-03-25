package com.bank.customer.exception;


public record ErrorResponse(
        String error,
        String message,
        int status
) {}
