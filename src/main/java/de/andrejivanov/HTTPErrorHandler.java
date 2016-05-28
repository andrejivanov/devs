package de.andrejivanov;


import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@ControllerAdvice
public class HTTPErrorHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(HTTPErrorHandler.class);

    @ExceptionHandler(HttpStatusCodeException.class)
    ResponseEntity<Object> handleHttpStatusCodeExceptions(HttpStatusCodeException exception, WebRequest request) {
        if (exception.getStatusCode().is5xxServerError()) {
            LOGGER.error(exception.getStatusText(), exception);
        }
        final RestResponseError responseError = new RestResponseError(exception.getStatusCode().value(),
                exception.getMessage(), ZonedDateTime.now(ZoneOffset.UTC).toString(), exception.getResponseBodyAsString());

        return handleExceptionInternal(exception, responseError, new HttpHeaders(), exception.getStatusCode(), request);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<Object> handleGenericExceptions(Exception exception, WebRequest request) {
        LOGGER.error(exception.getMessage(), exception);

        return handleExceptionInternal(exception,
                new RestResponseError(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage(), ZonedDateTime.now(ZoneOffset.UTC).toString(), Throwables.getStackTraceAsString(exception)),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    static class RestResponseError {
        private final int status;
        private final String message;
        private final String timestamp;
        private final String trace;

        RestResponseError(final int status, final String message, final String timestamp, final String trace) {
            this.status = status;
            this.message = message;
            this.timestamp = timestamp;
            this.trace = trace;
        }

        public int getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public String getTrace() {
            return trace;
        }
    }
}