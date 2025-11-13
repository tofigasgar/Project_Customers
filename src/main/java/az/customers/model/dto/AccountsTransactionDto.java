package az.customers.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Schema(name = "AccountsTransactionDto", description = "AccountTransactionDTO class")
public class AccountsTransactionDto {

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Schema(name = "AccountsTransactionDTORequest", description = "AccountTransactionDto  Request")
    public static class Request {
        @Schema(description = "The transaction amount", example = "1000.00")
        BigDecimal amount;

        @Schema(description = "The transaction description", example = "Deposit")
        String description;

        @Schema(description = "The transaction type", example = "DEPOSIT")
        String type;
    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Schema(name = "AccountsTransactionDtoResponse", description = "AccountTransactionDto  Response")
    public static class Response {

        @Schema(description = "Account-transaction ID", example = "1")
        Long id;

        @Schema(description = "The transaction amount", example = "1000.00")
        BigDecimal amount;

        @Schema(description = "The transaction before balance", example = "100000.00")
        BigDecimal beforeBalance;

        @Schema(description = "The transaction after balance", example = "99000.00")
        BigDecimal afterBalance;

        @Schema(description = "The transaction description", example = "Deposit")
        String description;

        @Schema(description = "The transaction type", example = "DEPOSIT or WITHDRAW")
        String type;

        @Schema(description = "The user account number", example = "AZ21BBBZ22111114107000345678")
        String accountNumber;
    }
}
