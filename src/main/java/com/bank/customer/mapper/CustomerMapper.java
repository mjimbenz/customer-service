package com.bank.customer.mapper;


import com.bank.customer.api.model.Customer;
import com.bank.customer.api.model.CustomerRequest;
import com.bank.customer.model.CustomerEntity;


public interface CustomerMapper {
    CustomerEntity toEntity(CustomerRequest dto);
    Customer toDto(CustomerEntity entity);
}

