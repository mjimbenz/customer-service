package com.bank.customer;


import com.bank.customer.api.model.CustomerRequest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CustomerRequestTest {

    @Test
    void testRequiredConstructor() {
        CustomerRequest req = new CustomerRequest(
                CustomerRequest.TypeEnum.PERSONAL,
                "DNI",
                "12345678",
                "Miguel Jiménez"
        );

        assertEquals(CustomerRequest.TypeEnum.PERSONAL, req.getType());
        assertEquals("DNI", req.getDocumentType());
        assertEquals("12345678", req.getDocumentNumber());
        assertEquals("Miguel Jiménez", req.getFullname());
    }

    @Test
    void testSettersAndGetters() {
        CustomerRequest req = new CustomerRequest();

        req.setType(CustomerRequest.TypeEnum.BUSINESS);
        req.setDocumentType("RUC");
        req.setDocumentNumber("20481234567");
        req.setFullname("Empresa SAC");
        req.setEmail("contacto@empresa.com");
        req.setPhone("987654321");

        assertEquals(CustomerRequest.TypeEnum.BUSINESS, req.getType());
        assertEquals("RUC", req.getDocumentType());
        assertEquals("20481234567", req.getDocumentNumber());
        assertEquals("Empresa SAC", req.getFullname());
        assertEquals("contacto@empresa.com", req.getEmail());
        assertEquals("987654321", req.getPhone());
    }

    @Test
    void testTypeEnum_fromValue() {
        assertEquals(
                CustomerRequest.TypeEnum.PERSONAL,
                CustomerRequest.TypeEnum.fromValue("PERSONAL")
        );

        assertEquals(
                CustomerRequest.TypeEnum.BUSINESS,
                CustomerRequest.TypeEnum.fromValue("BUSINESS")
        );
    }

    @Test
    void testTypeEnum_fromValue_error() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> CustomerRequest.TypeEnum.fromValue("INVALID")
        );

        assertTrue(ex.getMessage().contains("Unexpected value"));
    }

    @Test
    void testEqualsAndHashCode() {
        CustomerRequest r1 = new CustomerRequest(
                CustomerRequest.TypeEnum.PERSONAL,
                "DNI",
                "12345678",
                "Miguel"
        );

        CustomerRequest r2 = new CustomerRequest(
                CustomerRequest.TypeEnum.PERSONAL,
                "DNI",
                "12345678",
                "Miguel"
        );

        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    void testToString() {
        CustomerRequest req = new CustomerRequest(
                CustomerRequest.TypeEnum.PERSONAL,
                "DNI",
                "12345678",
                "Miguel"
        );

        String text = req.toString();

        assertTrue(text.contains("PERSONAL"));
        assertTrue(text.contains("DNI"));
        assertTrue(text.contains("12345678"));
        assertTrue(text.contains("Miguel"));
    }
}