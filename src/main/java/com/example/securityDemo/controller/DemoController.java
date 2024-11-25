package com.example.securityDemo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("/run")
    public ResponseEntity<String> run() {
        return ResponseEntity.of(Optional.of("Running"));
    }
}
