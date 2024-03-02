package com.aele.springauthapi.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.aele.springauthapi.dto.AuthenticationRequest;
import com.aele.springauthapi.dto.AuthenticationResponse;
import com.aele.springauthapi.entity.User;
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

       try {
           UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                   request.getUsername(),
                   request.getPassword());

           authenticationManager.authenticate(authToken); // ! Si la autenticacion falla se
           // lanza una excepcion

           User user = userRepository.findByUsername(request.getUsername()).get();

           String jwt = jwtService.generateToken(user, generateExtraClaims(user));

           return AuthenticationResponse
                   .builder()
                   .jwt(jwt)
                   .build();
       }
       catch (BadCredentialsException e) {
           throw new BadCredentialsException("Error en las credenciasles");
       }
    }

    private Map<String, Object> generateExtraClaims(User user) {
        return Map.of(
                "name", user.getName(),
                "role", user.getRole());
    }

}
