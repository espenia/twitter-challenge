package twitter.challenge.espenia.core.exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends BaseAPIException {

    public ConflictException(String message, Throwable cause) {
        super(message, cause, HttpStatus.CONFLICT);
    }

    public ConflictException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
