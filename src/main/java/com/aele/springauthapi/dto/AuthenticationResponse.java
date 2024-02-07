package com.aele.springauthapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
@AllArgsConstructor
public class AuthenticationResponse {

    @Getter @Setter
    private String jwt;
}
