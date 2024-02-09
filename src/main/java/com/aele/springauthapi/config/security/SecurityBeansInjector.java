package com.aele.springauthapi.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.aele.springauthapi.repository.UserRepository;

@Component
public class SecurityBeansInjector {

    // ! Cuando se usa Bean se puede hacer la inyeccion de dependencias en los
    // metodos
    // @Autowired
    // public AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    private UserRepository userRepository;

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {

        // Se crea el ProviderManaget implementando AuthenticationManager
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {

            return userRepository.findByUsername(username)
                    // .orElseThrow(() -> new RuntimeException("User not found"));
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        };
    }
}
