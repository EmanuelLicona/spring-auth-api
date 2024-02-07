package com.aele.springauthapi.dto;


import com.aele.springauthapi.util.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponse {

    @Getter @Setter
    private long id;

    @Getter @Setter
    private String username;

    @Getter @Setter
    private Role role;

    @Getter @Setter
    private String jwt;
}
