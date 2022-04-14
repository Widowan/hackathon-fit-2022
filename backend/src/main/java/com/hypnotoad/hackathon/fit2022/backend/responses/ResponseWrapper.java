package com.hypnotoad.hackathon.fit2022.backend.responses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseWrapper {
    public static ResponseEntity<Response> fail(int code, String message) {
        return ResponseEntity.status(code).body(new FailResponse(message));
    }

    public static ResponseEntity<Response> fail(HttpStatus code, String message) {
        return ResponseEntity.status(code).body(new FailResponse(message));
    }
}
