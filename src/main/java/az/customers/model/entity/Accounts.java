package az.customers.model.entity;

import az.customers.model.enums.AccountStatus;
import az.customers.model.enums.Currency;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Entity
@Getter
@Setter
@ToString
@Table(name = "accounts")
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Accounts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "The account id", example = "1")
    Long id;

    @Column(name = "balance")
    @Schema(description = "The user balance", example = "100000.00")
    BigDecimal balance;

    @Column(name = "account_number", unique = true)
    @Pattern(regexp = "^AZ\\d{2}[A-Z]{4}\\d{20}$", message = "Please enter the correct account number format")
    @Schema(description = "The user account number", example = "AZ21BBAZ00000000000012345678")
    String accountNumber;

    @Column(name = "currency", nullable = false)
    @Schema(description = "The user currency", example = "USD")
    @Enumerated(EnumType.STRING)
    Currency currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Schema(description = "The user status", example = "ACTIVE")
    AccountStatus status;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonBackReference
    Customer customer;

    @JsonManagedReference
    @OneToMany(mappedBy = "accounts", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<AccountTransaction> accountTransaction;

    @CreationTimestamp
    @Column(name = "created_at")
    @Schema(description = "The user createdAt", example = "01/01/2000")
    LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Schema(description = "The user updatedAt", example = "01/01/2000")
    LocalDateTime updatedAt;

    @Column(name = "active")
    @Schema(description = "The user active", example = "default 1")
    @Builder.Default
    Boolean active = true;

}
