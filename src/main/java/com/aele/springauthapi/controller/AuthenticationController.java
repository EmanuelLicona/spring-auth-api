package com.aele.springauthapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid AuthenticationRequest request) {

        AuthenticationResponse jwtDto = authenticationService.login(request); 

        return ResponseEntity.ok(jwtDto);
    }

    @PostMapping("/registera")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterRequest request) {
        // UserEntity user = UserEntity.builder()
        //         .name(request.getName())
        //         .username(request.getUsername())
        //         .password(passwordEncoder.encode(request.getPassword()))
        //         .role(Role.CUSTOMER)
        //         .build();

        // userRepository.save(user);

        // return ResponseEntity.ok(RegisterResponse.builder()
        //         .id(user.getId())
        //         .username(user.getUsername())
        //         .role(user.getRole())
        //         .jwt("este es un json web token")
        //         .build());

        throw new RuntimeException("No implementado");
    }

    @GetMapping("/public-access")
    public String publicAccess() {
        return "Este endpoint es publico";
    }

}
