package az.customers.model.entity;

import az.customers.model.enums.Gender;
import az.customers.util.converter.GenderAttributeConverter;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.List;

@Builder
@Entity
@Table(name = "customers")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "The customer id", example = "1")
    Long id;

    @Column(name = "name", nullable = false)
    @Schema(description = "The customer name", example = "Tofig")
    String name;

    @Column(name = "surname", nullable = false)
    @Schema(description = "The customer surname", example = "Asgarov")
    String surname;

    @Column(name = "fin", unique = true, length = 7)
    @Schema(description = "The user fin", example = "QQQQ123")
    @NotNull
    String fin;

    @Column(name = "passport_serial", unique = true, nullable = false)
    @Schema(description = "The user passport seria number", example = "AA111111")
    @Pattern(regexp = "^(AZE|AA)\\d{8}$", message = "Please enter the correct passport serial number format")
    String passportSerial;

    @Column(name = "birth_date")
    @Schema(description = "The user birthdate", example = "01/01/2000")
    @NotNull
    LocalDate birthDate;

    @Column(name = "email")
    @Email(message = "Please enter the correct email format")
    @Schema(description = "The user email", example = "tofigasgarov@gmail.com")
    String email;

    @Column(name = "phone")
    @Schema(description = "The user phone number", example = "010271701")
    @Pattern(regexp = "\\b(\\+?\\d{1,3}[- ]?)?\\d{9,10}\\b",
            message = "Please enter the correct phone number format")
    String phone;

    @Convert(converter = GenderAttributeConverter.class)
    @Schema(description = "Gender", example = "Male or Female")
    @Column(name = "gender", nullable = false)
    Gender gender;

    @JsonManagedReference
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    List<Accounts> accounts;

    @CreationTimestamp
    @Column(name = "created_at")
    @Schema(description = "The user createdAt", example = "01/01/2000")
    LocalDate createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Schema(description = "The user updatedAt", example = "01/01/2000")
    LocalDate updatedAt;

    @Column(name = "active")
    @Schema(description = "The user active", example = "default 1")
    @Builder.Default
    Boolean active = true;

}
