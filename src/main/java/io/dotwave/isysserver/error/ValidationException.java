package io.dotwave.isysserver.error;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidationException extends RuntimeException {
    private String message;
}
