package az.customers.exception;

import az.customers.model.enums.ErrorCodes;

public class ResourceAlreadyExistsException extends DPException {

    public ResourceAlreadyExistsException(String message, String errorUuid, ErrorCodes errorCode) {
        super(message, errorUuid, errorCode);
    }
}
