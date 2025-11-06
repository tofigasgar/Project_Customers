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
    PASSPORT_SERIAL_EXISTS(2006, "Passport Serial number already exists"),
    ACCOUNT_NUMBER_EXISTS(2007, "Account number already exists"),
    ACCOUNT_NOT_FOUND(2008, "Account not found"),
    INACTIVE_ACCOUNT(2009, "Inactive account"),
    INSUFFICIENT_FUNDS(2010, "Insufficient funds"),
    BLOCKED_ACCOUNT(2011, "Blocked account"),
    TRANSACTION_NOT_FOUND(2012, "Transaction not found");

    private final int code;
    private final String message;

    ErrorCodes(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
