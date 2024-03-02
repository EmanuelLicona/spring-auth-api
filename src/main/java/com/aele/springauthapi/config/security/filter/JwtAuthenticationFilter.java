package com.aele.springauthapi.config.security.filter;

import java.io.IOException;

import com.aele.springauthapi.config.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;


import com.aele.springauthapi.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerExceptionResolver;

//@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private HandlerExceptionResolver exceptionResolver;

    @Autowired
    private JwtService jwtService;

//    @Autowired
//    private UserRepository userRepository;

    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    public JwtAuthenticationFilter(HandlerExceptionResolver exceptionResolver) {
        this.exceptionResolver = exceptionResolver;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // * Get the header that contains the token
        String header = request.getHeader("Authorization");

        // * Get jwt token
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = header.split(" ")[1].trim();

        try {
            // * Get subject/username from jwt
            String username = jwtService.extractUsernameWithJwt(jwt); // ! <-- Throws ExpiredJwtException


            // * Set user in security context
//            UserEntity user = userRepository.findByUsername(username).get();
            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authToken);

            // * Continue filter chain
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            exceptionResolver.resolveException(request, response, null, e);
        }
    }

}
