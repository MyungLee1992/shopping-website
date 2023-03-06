package com.shoppingwebsite.shoppingwebsite.configuration;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class JwtResponse implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwt;
    public JwtResponse(String jwt) {
        super();
        this.jwt = jwt;
    }
}
