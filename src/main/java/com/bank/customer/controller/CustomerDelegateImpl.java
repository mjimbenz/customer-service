package com.bank.customer.controller;

import com.bank.customer.api.CustomerApiDelegate;
import com.bank.customer.api.model.Customer;
import com.bank.customer.api.model.CustomerRequest;
import com.bank.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CustomerDelegateImpl implements CustomerApiDelegate {


    private final CustomerService customerService;

    @Override
    public Mono<ResponseEntity<Flux<Customer>>> rootGet(ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.ok(customerService.findAll()));
    }

    @Override
    public Mono<ResponseEntity<Customer>> idGet(String id, ServerWebExchange exchange) {
        return customerService.findById(id)
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Customer>> rootPost(Mono<CustomerRequest> customerRequest, ServerWebExchange exchange) {
        return customerRequest.flatMap(request -> customerService.create(request)
                .map(ResponseEntity::ok));
    }

    @Override
    public Mono<ResponseEntity<Void>> idDelete(String id, ServerWebExchange exchange) {
        return customerService.delete(id)
                .thenReturn(ResponseEntity.noContent().build());
    }


    @Override
    public Mono<ResponseEntity<Void>> idPut(String id, Mono<CustomerRequest> customerRequest, ServerWebExchange exchange) {
        return customerRequest.flatMap(request -> customerService.update(id, request)
                .thenReturn(ResponseEntity.noContent().build()));
    }
}
