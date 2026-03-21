package com.bank.customer.service.impl;

import com.bank.customer.api.model.Customer;
import com.bank.customer.api.model.CustomerRequest;
import com.bank.customer.model.CustomerEntity;
import com.bank.customer.repository.CustomerRepository;
import com.bank.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;


    @Override
    public Flux<Customer> findAll() {
        return customerRepository.findAll()
                .map(this::toDto);
    }

    @Override
    public Mono<Customer> findById(String id) {
        return customerRepository.findById(id)
                .map(this::toDto);
    }

    @Override
    public Mono<Customer> create(CustomerRequest request) {
        return Mono.just(request)
                .map(this::toEntity)
                .flatMap(customerRepository::save)
                .map(this::toDto);
    }

    @Override
    public Mono<Customer> update(String id, CustomerRequest request) {
        return customerRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(
                        HttpStatus.NOT_FOUND,"Customer not found with id:" + id
                )))
                .flatMap(existing -> {
                    CustomerEntity updated = this.toEntity(request);
                    updated.setId(existing.getId());
                    return customerRepository.save(updated);
                })
                .map(this::toDto);
    }

    @Override
    public Mono<Void> delete(String id) {

        return customerRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Customer not found with id: " + id
                )))
                .flatMap(existing -> customerRepository.deleteById(existing.getId()));

    }


    private Customer toDto(CustomerEntity entity) {
        Customer dto = new Customer();
        dto.setId(entity.getId());
        dto.setType(entity.getType().equals("BUSINESS")? Customer.TypeEnum.BUSINESS : Customer.TypeEnum.PERSONAL);
        dto.setDocumentType(entity.getDocumentType());
        dto.setDocumentNumber(entity.getDocumentNumber());
        dto.setFullname(entity.getFullname());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        return dto;
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

}


