package az.customers.model.dto;

import az.customers.model.enums.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

public class AccountsTransactionDto {

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Request {
        @Schema(description = "The transaction amount", example = "1000.00")
        BigDecimal amount;

        @Schema(description = "The transaction description", example = "Deposit")
        String description;

        @Schema(description = "The transaction type", example = "DEPOSIT")
        TransactionType type;
    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Response {

        @Schema(description = "Account-transaction ID", example = "1")
        Long Id;

        @Schema(description = "The transaction amount", example = "1000.00")
        BigDecimal amount;

        @Schema(description = "The transaction before balance", example = "100000.00")
        BigDecimal beforeBalance;

        @Schema(description = "The transaction after balance", example = "99000.00")
        BigDecimal afterBalance;

        @Schema(description = "The transaction description", example = "Deposit")
        String description;

        @Schema(description = "The transaction type", example = "DEPOSIT")
        TransactionType type;

        @Schema(description = "The user account number", example = "123456789")
        String accountNumber;
    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Update {
        @Schema(description = "The transaction amount", example = "1000.00")
        BigDecimal amount;

        @Schema(description = "The transaction description", example = "Deposit")
        String description;

        @Schema(description = "The transaction type", example = "DEPOSIT")
        TransactionType type;
    }
}
