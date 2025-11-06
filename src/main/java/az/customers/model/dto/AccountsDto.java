package az.customers.model.dto;

import az.customers.model.entity.AccountTransaction;
import az.customers.model.enums.AccountStatus;
import az.customers.model.enums.Currency;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class AccountsDto {

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Response {

        @Schema(description = "The account id", example = "1")
        Long id;

        @Schema(description = "The user balance", example = "100000.00")
        BigDecimal balance;

        @Schema(description = "The user account number", example = "123456789")
        String accountNumber;

        @Schema(description = "The user currency", example = "USD")
        String currency;

        @Schema(description = "The user status", example = "ACTIVE")
        AccountStatus status;

        @Schema(description = "The customer fin", example = "QQQQ123")
        String customerFin;

        List<AccountsTransactionDto.Response> accountTransaction;

        @Schema(description = "The user createdAt", example = "01/01/2000")
        LocalDate createdAt;
    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Request {

        @Pattern(regexp = "^AZ\\d{2}[A-Z]{4}\\d{20}$", message = "Please enter the correct account number format")
        @Schema(description = "The user account number", example = "123456789")
        String accountNumber;

        @Schema(description = "The user currency", example = "USD")
        Currency currency;

    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Update {

        @Schema(description = "The user status", example = "ACTIVE")
        AccountStatus status;

    }
}

