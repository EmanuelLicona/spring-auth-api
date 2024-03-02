package com.aele.springauthapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aele.springauthapi.dto.AuthenticationRequest;
import com.aele.springauthapi.dto.AuthenticationResponse;
import com.aele.springauthapi.dto.RegisterRequest;
import com.aele.springauthapi.dto.RegisterResponse;
import com.aele.springauthapi.service.AuthenticationService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PreAuthorize("permitAll")
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid AuthenticationRequest request) {
        AuthenticationResponse jwtDto = authenticationService.login(request);
        return ResponseEntity.ok(jwtDto);
    }

    @PreAuthorize("permitAll")
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {

        throw new RuntimeException("No implementado");
    }

    @PreAuthorize("permitAll")
    @GetMapping("/public-access")
    public String publicAccess() {
        return "Este endpoint es publico";
    }

}
