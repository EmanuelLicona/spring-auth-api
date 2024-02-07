package com.aele.springauthapi.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.aele.springauthapi.dto.AuthenticationRequest;
import com.aele.springauthapi.dto.AuthenticationResponse;
import com.aele.springauthapi.entity.UserEntity;
import com.aele.springauthapi.repository.UserRepository;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    public AuthenticationResponse login(AuthenticationRequest request) {

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword());

        authenticationManager.authenticate(authToken); // ! Si la autenticacion falla se
                                                       // lanza una excepcion
        UserEntity user = userRepository.findByUsername(request.getUsername()).get();

        String jwt = jwtService.generateToken(user, generateExtraClaims(user));

        return AuthenticationResponse
                .builder()
                .jwt(jwt)
                .build();
    }

    private Map<String, Object> generateExtraClaims(UserEntity user) {
        return Map.of(
                "name", user.getName(),
                "role", user.getRole());
    }

}
