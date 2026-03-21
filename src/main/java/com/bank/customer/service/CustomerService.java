package com.bank.customer.service;


import com.bank.customer.api.model.Customer;
import com.bank.customer.api.model.CustomerRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {
    Flux<Customer> findAll();
    Mono<Customer> findById(String id);
    Mono<Customer> create(CustomerRequest request);
    Mono<Customer> update(String id, CustomerRequest request);
    Mono<Void> delete(String id);
}
