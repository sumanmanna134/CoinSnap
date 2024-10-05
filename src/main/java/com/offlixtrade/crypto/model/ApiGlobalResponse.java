package com.offlixtrade.crypto.model;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@Data
public class ApiGlobalResponse<T> {
    private int statusCode = 200;
    private String message = "Operation Executed Successfully";
    private T data;

    public ApiGlobalResponse(T data) {
        this.data = data;
    }

    public ApiGlobalResponse(T data, Optional<String> message) {
        this.data = data;
        this.message = String.valueOf(message);
    }

    public ApiGlobalResponse(T data, HttpStatus status) {
        this.data = data;
        this.statusCode = status.value();
    }
}
