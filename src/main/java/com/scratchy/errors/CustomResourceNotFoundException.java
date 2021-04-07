package com.scratchy.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CustomResourceNotFoundException extends RuntimeException {

    public CustomResourceNotFoundException() {
        super();
    }

    public CustomResourceNotFoundException(String message) {
        super(message);
    }
}
