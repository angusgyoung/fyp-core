package io.dotwave.isysserver.error;

import java.util.Objects;

public class ValidationException extends RuntimeException {
    private String message;

    public ValidationException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ValidationException)) return false;
        final ValidationException other = (ValidationException) o;
        if (!other.canEqual(this)) return false;
        final Object this$message = this.getMessage();
        final Object other$message = other.getMessage();
        return Objects.equals(this$message, other$message);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ValidationException;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $message = this.getMessage();
        result = result * PRIME + ($message == null ? 43 : $message.hashCode());
        return result;
    }

    public String toString() {
        return "ValidationException(message=" + this.getMessage() + ")";
    }
}
