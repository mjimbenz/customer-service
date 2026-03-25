package com.bank.customer.service.impl;

import com.bank.customer.api.model.Customer;
import com.bank.customer.api.model.CustomerRequest;
import com.bank.customer.model.CustomerEntity;
import com.bank.customer.repository.CustomerRepository;
import com.bank.customer.service.CustomerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Flux<Customer> findAll() {
        log.info("Fetching all customers");

        return customerRepository.findAll()
                .doOnNext(entity -> log.debug("Fetched customer: {}", entity.getId()))
                .map(this::toDto)
                .doOnComplete(() -> log.info("Completed fetching customers"));
    }

    @Override
    public Mono<Customer> findById(String id) {
        log.info("Fetching customer by id: {}", id);

        return customerRepository.findById(id)
                .switchIfEmpty(notFound(id))
                .doOnNext(entity -> log.debug("Found customer: {}", entity.getId()))
                .map(this::toDto);
    }

    @Override
    public Mono<Customer> create(CustomerRequest request) {
        log.info("Creating new customer with documentNumber: {}", request.getDocumentNumber());

        return Mono.just(request)
                .map(this::toEntity)
                .flatMap(customerRepository::save)
                .doOnSuccess(saved -> log.info("Customer created with id: {}", saved.getId()))
                .map(this::toDto);
    }

    @Override
    public Mono<Customer> update(String id, CustomerRequest request) {
        log.info("Updating customer with id: {}", id);

        return customerRepository.findById(id)
                .switchIfEmpty(notFound(id))
                .flatMap(existing -> {
                    CustomerEntity updated = toEntity(request);
                    updated.setId(existing.getId());

                    log.debug("Updating customer entity: {}", updated.getId());

                    return customerRepository.save(updated);
                })
                .doOnSuccess(updated -> log.info("Customer updated: {}", updated.getId()))
                .map(this::toDto);
    }

    @Override
    public Mono<Void> delete(String id) {
        log.info("Deleting customer with id: {}", id);

        return customerRepository.findById(id)
                .switchIfEmpty(notFound(id))
                .flatMap(existing -> {
                    log.debug("Deleting customer: {}", existing.getId());
                    return customerRepository.deleteById(existing.getId());
                })
                .doOnSuccess(v -> log.info("Customer deleted: {}", id));
    }

    // ==========================
    //       MAPPERS
    // ==========================

    private Customer toDto(CustomerEntity entity) {
        return new Customer()
                .id(entity.getId())
                .type(Customer.TypeEnum.valueOf(entity.getType()))
                .documentType(entity.getDocumentType())
                .documentNumber(entity.getDocumentNumber())
                .fullname(entity.getFullname())
                .email(entity.getEmail())
                .phone(entity.getPhone());
    }

    private CustomerEntity toEntity(CustomerRequest req) {
        CustomerEntity entity = new CustomerEntity();
        entity.setType(req.getType().getValue());
        entity.setDocumentType(req.getDocumentType());
        entity.setDocumentNumber(req.getDocumentNumber());
        entity.setFullname(req.getFullname());
        entity.setEmail(req.getEmail());
        entity.setPhone(req.getPhone());
        return entity;
    }

    // ==========================
    //  COMMON ERROR HANDLING
    // ==========================

    private <T> Mono<T> notFound(String id) {
        log.warn("Customer not found with id: {}", id);
        return Mono.error(new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Customer not found with id: " + id
        ));
    }
}