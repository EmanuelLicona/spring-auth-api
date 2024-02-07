package com.aele.springauthapi.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.aele.springauthapi.config.security.filter.JwtAuthenticationFilter;
import com.aele.springauthapi.util.Permission;

@Configuration
@EnableWebSecurity // Forma para habilitar por definiendo las request
public class HttpSecurityConfig {

    // ! Se injecta el DaoAuthenticationProvider que se define en
    // ! SecurityBeansInjector
    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                .csrf(csrfConfig -> csrfConfig.disable())
                .sessionManagement(sessionMangConfig -> sessionMangConfig
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // ! Por convenio el filtro JwtAuthenticationFilter debe ser anterior al filtro UsernamePasswordAuthenticationFilter
                .authorizeHttpRequests(authConfig -> {

                    authConfig.requestMatchers(HttpMethod.POST, "/auth/login").permitAll();
                    authConfig.requestMatchers(HttpMethod.GET, "/auth/public-access").permitAll();
                    authConfig.requestMatchers("/error").permitAll();

                    authConfig.requestMatchers(HttpMethod.GET, "/products")
                            .hasAuthority(Permission.READ_ALL_PRODUCTS.name());

                    authConfig.requestMatchers(HttpMethod.POST, "/products")
                            .hasAuthority(Permission.SAVE_ONE_PRODUCT.name());


                    // authConfig.anyRequest().authenticated();
                    // authConfig.anyRequest().denyAll();
                    authConfig.anyRequest().permitAll();

                });

        return http.build();
    }

}