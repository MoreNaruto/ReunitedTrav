package com.tmorris.reunitedtrav.controllers.jsonbodies;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationReponse {
    private String jwt;
}
