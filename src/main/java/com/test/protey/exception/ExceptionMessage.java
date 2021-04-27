package com.test.protey.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Getter
@Setter
public class ExceptionMessage {

    @JsonProperty("status")
    private HttpStatus status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("timestamp")
    private String timestamp;

    ExceptionMessage(HttpStatus status, String message) {
        this.status = status;
        this.message = message != null ? message : status.getReasonPhrase();
        this.timestamp = Instant.now().toString();
    }
}
