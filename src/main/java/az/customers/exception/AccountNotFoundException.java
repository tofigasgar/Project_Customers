package az.customers.exception;

import az.customers.model.enums.ErrorCodes;

public class AccountNotFoundException extends DPException{

    public AccountNotFoundException(String message, String errorUuid, ErrorCodes errorCode) {
        super(message, errorUuid, errorCode);
    }

    public AccountNotFoundException(ErrorCodes errorCode, String message) {
        super(errorCode, message);
    }
}
