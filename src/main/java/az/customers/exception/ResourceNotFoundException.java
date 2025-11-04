package az.customers.exception;

import az.customers.model.enums.ErrorCodes;

public class ResourceNotFoundException extends DPException {

    public ResourceNotFoundException(String message, String errorUuid, ErrorCodes errorCode) {
        super(message, errorUuid, errorCode);
    }
}
