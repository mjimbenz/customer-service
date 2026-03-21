package com.bank.customer;


import com.bank.customer.api.model.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    void testRequiredConstructor() {
        Customer c = new Customer(
                "id123",
                Customer.TypeEnum.PERSONAL,
                "DNI",
                "12345678",
                "Miguel Jiménez"
        );

        assertEquals("id123", c.getId());
        assertEquals(Customer.TypeEnum.PERSONAL, c.getType());
        assertEquals("DNI", c.getDocumentType());
        assertEquals("12345678", c.getDocumentNumber());
        assertEquals("Miguel Jiménez", c.getFullname());
    }

    @Test
    void testSettersAndGetters() {
        Customer c = new Customer();

        c.setId("id123");
        c.setType(Customer.TypeEnum.BUSINESS);
        c.setDocumentType("RUC");
        c.setDocumentNumber("20481234567");
        c.setFullname("Tech Solutions");
        c.setEmail("info@tech.com");
        c.setPhone("999999999");

        assertEquals("id123", c.getId());
        assertEquals(Customer.TypeEnum.BUSINESS, c.getType());
        assertEquals("RUC", c.getDocumentType());
        assertEquals("20481234567", c.getDocumentNumber());
        assertEquals("Tech Solutions", c.getFullname());
        assertEquals("info@tech.com", c.getEmail());
        assertEquals("999999999", c.getPhone());
    }

    @Test
    void testTypeEnum_fromValue() {
        assertEquals(
                Customer.TypeEnum.PERSONAL,
                Customer.TypeEnum.fromValue("PERSONAL")
        );

        assertEquals(
                Customer.TypeEnum.BUSINESS,
                Customer.TypeEnum.fromValue("BUSINESS")
        );
    }

    @Test
    void testTypeEnum_fromValue_invalid() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> Customer.TypeEnum.fromValue("WRONG")
        );

        assertTrue(ex.getMessage().contains("Unexpected value"));
    }

    @Test
    void testEqualsAndHashCode() {
        Customer c1 = new Customer(
                "id1",
                Customer.TypeEnum.PERSONAL,
                "DNI",
                "12345678",
                "Miguel"
        );

        Customer c2 = new Customer(
                "id1",
                Customer.TypeEnum.PERSONAL,
                "DNI",
                "12345678",
                "Miguel"
        );

        assertEquals(c1, c2);
        assertEquals(c1.hashCode(), c2.hashCode());
    }

    @Test
    void testToString() {
        Customer c = new Customer(
                "id999",
                Customer.TypeEnum.PERSONAL,
                "DNI",
                "98765432",
                "Alguien"
        );

        String text = c.toString();

        assertTrue(text.contains("id999"));
        assertTrue(text.contains("PERSONAL"));
        assertTrue(text.contains("98765432"));
        assertTrue(text.contains("Alguien"));
    }
}


