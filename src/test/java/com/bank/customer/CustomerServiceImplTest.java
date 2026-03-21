package com.bank.customer;

import com.bank.customer.api.model.CustomerRequest;
import com.bank.customer.model.CustomerEntity;
import com.bank.customer.repository.CustomerRepository;
import com.bank.customer.service.impl.CustomerServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository repository;

    @InjectMocks
    private CustomerServiceImpl service;

    private CustomerRequest request;
    private CustomerEntity entity;


    @BeforeEach
    void setup() {

        request = new CustomerRequest(
                CustomerRequest.TypeEnum.PERSONAL,
                "DNI",
                "12345678",
                "Miguel Jiménez"
        );

        entity = new CustomerEntity();
        entity.setId("65f1203cc0a19d5b6de70871");
        entity.setType("PERSONAL");
        entity.setDocumentType("DNI");
        entity.setDocumentNumber("12345678");
        entity.setFullname("Miguel Jiménez");
        entity.setEmail("miguel@example.com");
        entity.setPhone("987654321");
    }

    // ------------------------
    // CREATE
    // ------------------------
    @Test
    void create_shouldReturnCustomerDto() {
        when(repository.save(any(CustomerEntity.class)))
                .thenReturn(Mono.just(entity));

        StepVerifier.create(service.create(request))
                .assertNext(result -> {
                    assertEquals("65f1203cc0a19d5b6de70871", result.getId());
                    assertEquals("Miguel Jiménez", result.getFullname());
                    assertEquals("12345678", result.getDocumentNumber());
                })
                .verifyComplete();

        verify(repository, times(1)).save(any(CustomerEntity.class));
    }

    // ------------------------
    // UPDATE OK
    // ------------------------
    @Test
    void update_whenIdExists_shouldUpdateAndReturnCustomer() {

        when(repository.findById("65f1203cc0a19d5b6de70871"))
                .thenReturn(Mono.just(entity));

        when(repository.save(any(CustomerEntity.class)))
                .thenReturn(Mono.just(entity));

        StepVerifier.create(service.update("65f1203cc0a19d5b6de70871", request))
                .assertNext(result -> {
                    assertEquals("65f1203cc0a19d5b6de70871", result.getId());
                    assertEquals("Miguel Jiménez", result.getFullname());
                })
                .verifyComplete();
    }

    // ------------------------
    // UPDATE NOT FOUND
    // ------------------------
    @Test
    void update_whenIdNotFound_shouldThrowNotFound() {

        when(repository.findById("notfound"))
                .thenReturn(Mono.empty());

        StepVerifier.create(service.update("notfound", request))
                .expectErrorMatches(err ->
                        err instanceof ResponseStatusException &&
                                ((ResponseStatusException) err)
                                        .getStatusCode().equals(HttpStatus.NOT_FOUND)
                )
                .verify();
    }

    // ------------------------
    // FIND ALL
    // ------------------------
    @Test
    void findAll_returnsCustomers() {
        when(repository.findAll()).thenReturn(Flux.just(entity));

        StepVerifier.create(service.findAll())
                .assertNext(c ->
                        assertEquals("65f1203cc0a19d5b6de70871", c.getId())
                )
                .verifyComplete();
    }

    // ------------------------
    // FIND BY ID OK
    // ------------------------
    @Test
    void findById_returnsCustomer() {
        when(repository.findById("65f1203cc0a19d5b6de70871"))
                .thenReturn(Mono.just(entity));

        StepVerifier.create(service.findById("65f1203cc0a19d5b6de70871"))
                .assertNext(c -> assertEquals("Miguel Jiménez", c.getFullname()))
                .verifyComplete();
    }

    // ------------------------
    // DELETE OK
    // ------------------------
    @Test
    void delete_whenExists_ok() {
        when(repository.findById("65f1203cc0a19d5b6de70871"))
                .thenReturn(Mono.just(entity));
        when(repository.deleteById(entity.getId())).thenReturn(Mono.empty());

        StepVerifier.create(service.delete("65f1203cc0a19d5b6de70871"))
                .verifyComplete();
    }

    // ------------------------
    // DELETE NOT FOUND
    // ------------------------
    @Test
    void delete_whenNotFound_throwsError() {
        when(repository.findById("123")).thenReturn(Mono.empty());

        StepVerifier.create(service.delete("123"))
                .expectError(ResponseStatusException.class)
                .verify();
    }
}
