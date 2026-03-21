package com.bank.customer.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("customers")
public class CustomerEntity {
    @Id
    private String id;
    private String type;
    private String documentType;
    private String documentNumber;
    private String fullname;
    private String email;
    private String phone;
}

