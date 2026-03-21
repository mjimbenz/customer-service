package com.bank.customer.repository;

import com.bank.customer.model.CustomerEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CustomerRepository extends ReactiveMongoRepository<CustomerEntity, String> {
}
