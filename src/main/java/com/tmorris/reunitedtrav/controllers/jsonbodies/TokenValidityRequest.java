package com.tmorris.reunitedtrav.controllers.jsonbodies;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenValidityRequest implements Serializable {
    private static final long serialVersionUID = 4730990961743879244L;

    private String username;
    private String token;
}
