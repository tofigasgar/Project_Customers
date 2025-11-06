package az.customers.exception;

import az.customers.model.enums.ErrorCodes;

public class InactiveAccountException extends DPException{
    public InactiveAccountException(String message, String errorUuid, ErrorCodes errorCode) {
        super(message, errorUuid, errorCode);
    }

    public InactiveAccountException(ErrorCodes errorCode, String message) {
        super(errorCode, message);
    }
}
