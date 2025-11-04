package az.customers.model.enums;

import lombok.Getter;

@Getter
public enum ErrorCodes {

    // General errors
    INTERNAL_SERVER_ERROR(1000, "Internal server error"),
    VALIDATION_ERROR(1001, "Validation error"),
    RESOURCE_NOT_FOUND(1002, "Resource not found"),

    // User related errors
    CUSTOMER_NOT_FOUND(2000, "User not found"),
    CUSTOMER_ALREADY_EXISTS(2001, "User already exists"),
    FIN_ALREADY_EXISTS(2003, "Fin already exists"),
    INVALID_CREDENTIALS(2004, "Invalid credentials"),
    USER_DISABLED(2005, "User is disabled"),
    PASSPORT_SERIAL_NUMBER(2006, "Passport Serial number already exists");

    private final int code;
    private final String message;

    ErrorCodes(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
