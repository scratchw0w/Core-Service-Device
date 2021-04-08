package com.scratchy.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Device with such id is already exist")
public class DeviceIdIsAlreadyExistException extends RuntimeException {

    public DeviceIdIsAlreadyExistException() {
        super("Device with such id is already exist");
    }

    public DeviceIdIsAlreadyExistException(String message) {
        super(message);
    }

    public DeviceIdIsAlreadyExistException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
