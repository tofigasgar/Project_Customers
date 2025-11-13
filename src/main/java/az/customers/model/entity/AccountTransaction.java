package az.customers.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Entity
@Getter
@Setter
@ToString
@Table(name = "account_transaction")
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Account-transaction ID", example = "1")
    Long Id;

    @Column(name = "amount", nullable = false)
    @Schema(description = "The transaction amount", example = "1000.00")
    BigDecimal amount;

    @Column(name = "before_balance", nullable = false)
    @Schema(description = "The transaction before balance", example = "100000.00")
    BigDecimal beforeBalance;

    @Column(name = "after_balance", nullable = false)
    @Schema(description = "The transaction after balance", example = "99000.00")
    BigDecimal afterBalance;

    @Column(name = "description")
    @Schema(description = "The transaction description", example = "Deposit")
    String description;

    @Column(name = "type", nullable = false)
    @Schema(description = "The transaction type", example = "DEPOSIT")
    String type;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    Accounts accounts;

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
