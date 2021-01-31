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
public class AuthenticationResponse implements Serializable {
    private static final long serialVersionUID = 3930190626895020902L;
    private String jwt;
}
