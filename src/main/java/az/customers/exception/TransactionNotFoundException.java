package az.customers.exception;

import az.customers.model.enums.ErrorCodes;

public class TransactionNotFoundException extends DPException{
    public TransactionNotFoundException(String message, String errorUuid, ErrorCodes errorCode) {
        super(message, errorUuid, errorCode);
    }

    public TransactionNotFoundException(ErrorCodes errorCode, String message) {
        super(errorCode, message);
    }
}
