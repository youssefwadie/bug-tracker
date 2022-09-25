package com.github.youssefwadie.bugtracker.util;


import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Getter
public class SimpleResponseBody {
    // Required
    private final int status;
    private final String description;

    // Optional
    private final String message;
    private final String timestamp;


    private SimpleResponseBody(Builder builder) {
        this.status = builder.status;
        this.description = builder.description;

        this.message = builder.message;
        this.timestamp = builder.timestamp.toString();
    }

    public static Builder builder(HttpStatus httpStatus) {
        return new Builder(httpStatus);
    }

    public static class Builder {
        private final int status;
        private final String description;

        private String message;
        private Instant timestamp;

        public Builder(HttpStatus httpStatus) {
            this.description = httpStatus.getReasonPhrase();
            this.status = httpStatus.value();

            this.message = "";
            this.timestamp = Instant.now();
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder timestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public SimpleResponseBody build() {
            return new SimpleResponseBody(this);
        }
    }


}
