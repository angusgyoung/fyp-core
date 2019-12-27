package io.dotwave.isysserver.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@Slf4j
@ControllerAdvice
public class GlobalErrorHandler {

    @ResponseBody
    @ExceptionHandler(value = ValidationException.class)
    public ResponseEntity<?> handleValidationException(ValidationException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
}
