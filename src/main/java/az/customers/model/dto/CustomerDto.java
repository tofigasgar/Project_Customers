package az.customers.model.dto;

import az.customers.model.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Schema(name = "CustomerDTO", description = "CustomerDTO class")
public class CustomerDto {

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Schema(name = "CustomerRequest", description = "Customer request")
    public static class Request {

        @Schema(description = "The customer name", example = "Tofig")
        String name;

        @Schema(description = "The customer surname", example = "Asgarov")
        String surname;

        @Size(max = 7, min = 7, message = "The user fin must be 7 characters long")
        @Schema(description = "The user fin", example = "QQQQ123")
        String fin;

        @Pattern(regexp = "^(AZE|AA)\\d{8}$", message = "Please enter the correct passport serial number format")
        @Schema(description = "The user passport seria number", example = "AZE57155509")
        String passportSerial;

        @Schema(description = "The user birthdate", example = "1997-11-25")
        LocalDate birthDate;

        @Email(message = "Please enter the correct email format")
        @Schema(description = "The user email", example = "tofigasgarov@gmail.com")
        String email;

        @Pattern(regexp = "\\b(\\+?\\d{1,3}[- ]?)?\\d{9,10}\\b",
                message = "Please enter the correct phone number format")
        @Schema(description = "The user phone number", example = "010271701")
        String phone;

        @Schema(description = "Gender", example = "MALE")
        Gender gender;

    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Schema(name = "CustomerResponse", description = "Customer response")
    public static class Response {
        @Schema(description = "The customer id", example = "1")
        Long id;

        @Schema(description = "The customer name", example = "Tofig")
        String name;

        @Schema(description = "The customer surname", example = "Asgarov")
        String surname;

        @Schema(description = "The user fin", example = "QQQQ123")
        String fin;

        @Schema(description = "The user passport seria number", example = "AZE57155509")
        String passportSerial;

        @Schema(description = "The user birthdate", example = "1997-11-25")
        LocalDate birthDate;

        @Schema(description = "The user email", example = "tofigasgarov@gmail.com")
        String email;

        @Schema(description = "The user phone number", example = "010271701")
        String phone;

        @Schema(description = "Gender", example = "MALE")
        Gender gender;

        // List<AccountsDto.Response> accounts;

        @Schema(description = "The user createdAt", example = "2025-11-09")
        LocalDate createdAt;
    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Schema(name = "CustomerUpdate", description = "Customer update request")
    public static class Update {

        @Schema(description = "The customer name", example = "Tofig")
        String name;

        @Schema(description = "The customer surname", example = "Asgarov")
        String surname;

        @Schema(description = "The user passport seria number", example = "AZE57155509")
        @Pattern(regexp = "^(AZE|AA)\\d{8}$", message = "Please enter the correct passport serial number format")
        String passportSerial;

        @Email(message = "Please enter the correct email format")
        @Schema(description = "The user email", example = "tofigasgarov@gmail.com")
        String email;

        @Pattern(regexp = "\\b(\\+?\\d{1,3}[- ]?)?\\d{9,10}\\b",
                message = "Please enter the correct phone number format")
        @Schema(description = "The user phone number", example = "010271701")
        String phone;

    }
}
