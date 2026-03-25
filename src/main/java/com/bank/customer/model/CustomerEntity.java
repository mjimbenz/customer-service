package com.bank.customer.model;


import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "customers")

@CompoundIndex(
        name = "idx_document_type_number",
        def = "{'documentType': 1, 'documentNumber': 1}",
        unique = true
)
public class CustomerEntity {

    @Id
    private String id;

    @NotBlank(message = "El tipo de cliente es obligatorio")
    @Pattern(
            regexp = "BUSINESS|PERSONAL",
            message = "El tipo de cliente debe ser BUSINESS o PERSONAL"
    )
    private String type;

    @NotBlank(message = "El tipo de documento es obligatorio")
    @Pattern(
            regexp = "DNI|CE|PASSPORT",
            message = "El tipo de documento debe ser DNI, CE o PASSPORT"
    )
    private String documentType;

    @NotBlank(message = "El número de documento es obligatorio")
    @Indexed(unique = true)
    @Size(min = 5, max = 20)
    private String documentNumber;

    @NotBlank(message = "El nombre completo es obligatorio")
    @Size(min = 3, max = 100)
    private String fullname;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Email inválido")
    @Indexed(unique = true)
    private String email;

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(
            regexp = "^[0-9]{7,15}$",
            message = "El teléfono debe tener entre 7 y 15 dígitos"
    )
    private String phone;
}

