package az.customers.exception;

import az.customers.model.enums.ErrorCodes;

public class CustomerNotFoundException extends DPException {

    public CustomerNotFoundException(String message, String errorUuid, ErrorCodes errorCode) {
        super(message, errorUuid, errorCode);
    }

    public CustomerNotFoundException(ErrorCodes errorCodes, String errorMessage) {
        super(errorCodes, errorMessage);
    }
}
