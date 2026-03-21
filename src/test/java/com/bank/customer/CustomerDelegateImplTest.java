package com.bank.customer;


import com.bank.customer.api.model.Customer;
import com.bank.customer.api.model.CustomerRequest;
import com.bank.customer.controller.CustomerDelegateImpl;
import com.bank.customer.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerDelegateImplTest {

    @Mock
    private CustomerService service;

    @InjectMocks
    private CustomerDelegateImpl delegate;


    private ServerWebExchange exchange;



    @Test
    void rootGet_shouldReturnOkAndFlux() {
        Customer c = new Customer();
        c.setId("123");

        when(service.findAll()).thenReturn(Flux.just(c));

        StepVerifier.create(delegate.rootGet(exchange))
                .assertNext(response -> {
                    assertEquals(HttpStatus.OK, response.getStatusCode());
                    assertNotNull(response.getBody());
                })
                .verifyComplete();
    }


    @Test
    void rootPost_shouldReturnOkWithCustomer() {
        CustomerRequest req = new CustomerRequest(
                CustomerRequest.TypeEnum.PERSONAL,
                "DNI",
                "12345678",
                "Miguel Jiménez"
        );

        Customer created = new Customer(
                "abc123",
                Customer.TypeEnum.PERSONAL,
                "DNI",
                "12345678",
                "Miguel Jiménez"
        );

        when(service.create(req)).thenReturn(Mono.just(created));

        StepVerifier.create(delegate.rootPost(Mono.just(req), exchange))
                .assertNext(resp -> {
                    assertEquals(HttpStatus.OK, resp.getStatusCode());
                    assertEquals("abc123", resp.getBody().getId());
                })
                .verifyComplete();
    }



    @Test
    void rootPost_shouldPropagateErrorFromService() {
        CustomerRequest req = new CustomerRequest();

        when(service.create(any()))
                .thenReturn(Mono.error(new RuntimeException("Creation failed")));

        StepVerifier.create(delegate.rootPost(Mono.just(req), exchange))
                .expectError(RuntimeException.class)
                .verify();
    }




    @Test
    void idDelete_shouldReturn204() {
        when(service.delete("123")).thenReturn(Mono.empty());

        StepVerifier.create(delegate.idDelete("123", null))
                .expectNextMatches(resp -> resp.getStatusCode().is2xxSuccessful())
                .verifyComplete();
    }

    @Test
    void idDelete_whenError_returnsError() {
        when(service.delete("123"))
                .thenReturn(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));

        StepVerifier.create(delegate.idDelete("123", null))
                .expectError(ResponseStatusException.class)
                .verify();
    }

    @Test
    void idPut_shouldReturn204() {
        CustomerRequest req = new CustomerRequest();
        req.setFullname("Test");

        when(service.update(eq("123"), any()))
                .thenReturn(Mono.just(new Customer()));

        StepVerifier.create(
                        delegate.idPut("123", Mono.just(req), null)
                ).expectNextMatches(resp -> resp.getStatusCode().equals(HttpStatus.NO_CONTENT))
                .verifyComplete();
    }
}
