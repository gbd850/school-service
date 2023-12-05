package dev.peter.springdatajpapractice.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorMessage> responseStatusException(ResponseStatusException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage().split("\\\"")[1]);
        return new ResponseEntity<>(errorMessage, exception.getStatusCode());
    }
}
