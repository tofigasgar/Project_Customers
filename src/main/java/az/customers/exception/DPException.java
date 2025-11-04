package az.customers.exception;

import az.customers.model.enums.ErrorCodes;
import lombok.Getter;

@Getter
public class DPException extends RuntimeException {

    private final String message;

    private final String errorUuid;

    private final ErrorCodes errorCode;


    public DPException(String message, String errorUuid, ErrorCodes errorCode) {
        super(message);
        this.message = message;
        this.errorUuid = errorUuid;
        this.errorCode = errorCode;
    }

    public DPException(String message, ErrorCodes errorCode) {
        this(null, message, errorCode);
    }

    public DPException() {
    }
}
