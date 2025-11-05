package az.customers.model.dto;

import az.customers.model.entity.Accounts;
import az.customers.model.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

public class CustomerDto {

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Request {

        @Schema(description = "The customer name", example = "Tofig")
        String name;

        @Schema(description = "The customer surname", example = "Asgarov")
        String surname;

        @Schema(description = "The user fin", example = "QQQQ123")
        String fin;

        @Schema(description = "The user passport seria number", example = "AA111111")
        String passportSerial;

        @Schema(description = "The user birthdate", example = "01/01/2000")
        LocalDate birthDate;

        @Schema(description = "The user email", example = "tofigasgarov@gmail.com")
        String email;

        @Schema(description = "The user phone number", example = "010271701")
        String phone;

        @Schema(description = "Gender", example = "Male or Female")
        Gender gender;

    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Response {
        @Schema(description = "The customer id", example = "1")
        Long id;

        @Schema(description = "The customer name", example = "Tofig")
        String name;

        @Schema(description = "The customer surname", example = "Asgarov")
        String surname;

        @Schema(description = "The user fin", example = "QQQQ123")
        String fin;

        @Schema(description = "The user passport seria number", example = "AA111111")
        String passportSerial;

        @Schema(description = "The user birthdate", example = "01/01/2000")
        LocalDate birthDate;

        @Schema(description = "The user email", example = "tofigasgarov@gmail.com")
        String email;

        @Schema(description = "The user phone number", example = "010271701")
        String phone;

        @Schema(description = "Gender", example = "Male or Female")
        Gender gender;

        List<Accounts> accounts;

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
    public static class Update {

        @Schema(description = "The customer name", example = "Tofig")
        String name;

        @Schema(description = "The customer surname", example = "Asgarov")
        String surname;

        @Schema(description = "The user passport seria number", example = "AA111111")
        String passportSerial;

        @Schema(description = "The user email", example = "tofigasgarov@gmail.com")
        String email;

        @Schema(description = "The user phone number", example = "010271701")
        String phone;

    }
}
