package az.customers.exception.handler;

import az.customers.exception.AccountNotFoundException;
import az.customers.exception.CustomerNotFoundException;
import az.customers.exception.ResourceAlreadyExistsException;
import az.customers.exception.ResourceNotFoundException;
import az.customers.logger.DPLogger;
import az.customers.model.enums.ErrorCodes;
import az.customers.model.response.CommonErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final DPLogger logger = DPLogger.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CommonErrorResponse handlerCustomerNotFoundException(
            CustomerNotFoundException ex, HttpServletRequest request) {
        logger.error("CustomerNotFound error occurred: {}", ex.getMessage(), ex);
        return CommonErrorResponse.error(
                ex.getMessage(), ex.getErrorCode(), request.getRequestURI());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CommonErrorResponse handlerResourceNotFoundException(
            ResourceNotFoundException ex, HttpServletRequest request) {
        logger.error("ResourceNotFound error occurred: {}", ex.getMessage(), ex);
        return CommonErrorResponse.error(
                ex.getMessage(), ex.getErrorCode(), request.getRequestURI());
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonErrorResponse handlerResourceAlreadyExistsException(
            ResourceAlreadyExistsException ex, HttpServletRequest request) {
        logger.error("ResourceAlreadyExists error occurred: {}", ex.getMessage(), ex);
        return CommonErrorResponse.error(
                ex.getMessage(), ex.getErrorCode(), request.getRequestURI());
    }

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CommonErrorResponse handlerAccountNotFoundException(
            AccountNotFoundException ex, HttpServletRequest request) {
        logger.error("AccountNotFound error occurred: {}", ex.getMessage(), ex);
        return CommonErrorResponse.error(
                ex.getMessage(), ex.getErrorCode(), request.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonErrorResponse> handlerMethodArgumentNotValidException(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        logger.error("MethodArgumentNotValid error occurred: {}", ex.getMessage(), ex);
        HashMap<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        String message = "Validation error occurred: " + errors;

        CommonErrorResponse errorResponse = CommonErrorResponse.error(
                message, ErrorCodes.VALIDATION_ERROR, request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
