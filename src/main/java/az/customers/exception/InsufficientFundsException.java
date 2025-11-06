package az.customers.exception;

import az.customers.model.enums.ErrorCodes;

public class InsufficientFundsException extends DPException{
    public InsufficientFundsException(String message, String errorUuid, ErrorCodes errorCode) {
        super(message, errorUuid, errorCode);
    }

    public InsufficientFundsException(ErrorCodes errorCode, String message) {
        super(errorCode, message);
    }
}
