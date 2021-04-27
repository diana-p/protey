package com.test.protey.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PersonNotFoundException.class)
    protected ResponseEntity<Object> handlePersonNotFoundException(Exception e, WebRequest request) {
        ExceptionMessage message = new ExceptionMessage(HttpStatus.INTERNAL_SERVER_ERROR, "PersonNotFound");
        return helpToHandleExceptionInternal(e, HttpStatus.BAD_REQUEST, message, request, new HttpHeaders());
    }

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<Object> handleValidationException(Exception e, WebRequest request) {
        ExceptionMessage message = new ExceptionMessage(HttpStatus.INTERNAL_SERVER_ERROR, "ValidationException");
        return helpToHandleExceptionInternal(e, HttpStatus.BAD_REQUEST, message, request, new HttpHeaders());
    }

    private ResponseEntity<Object> helpToHandleExceptionInternal(Exception e,
                                                                 HttpStatus status,
                                                                 ExceptionMessage message,
                                                                 WebRequest request,
                                                                 HttpHeaders headers) {
        if (HttpStatus.INTERNAL_SERVER_ERROR == status) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, e, WebRequest.SCOPE_REQUEST);
        }

        return new ResponseEntity<>(message, headers, status);
    }
}
