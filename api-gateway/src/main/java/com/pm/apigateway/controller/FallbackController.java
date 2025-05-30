package com.pm.apigateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {
    @GetMapping("/fallback/patient-service")
    public ResponseEntity<String> patientServiceFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Patient service is temporarily unavailable. Please try again later.");
    }

    @GetMapping("/fallback/auth-service")
    public ResponseEntity<String> authServiceFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Auth service is temporarily unavailable. Please try again later.");
    }
}
