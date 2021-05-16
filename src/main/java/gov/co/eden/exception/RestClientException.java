package gov.co.eden.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * Exception class that represents errors during client communication with external services.
 */
@Data
public class RestClientException extends RuntimeException {

    final HttpStatus httpStatus;

    public RestClientException(String errorMessage, Throwable err, HttpStatus httpStatus) {
        super(errorMessage, err);
        this.httpStatus = httpStatus;
    }

    public RestClientException(String errorMessage, HttpStatus httpStatus) {
        super(errorMessage);
        this.httpStatus = httpStatus;
    }
}
