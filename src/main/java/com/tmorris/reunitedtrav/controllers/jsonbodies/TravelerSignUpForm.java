package com.tmorris.reunitedtrav.controllers.jsonbodies;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TravelerSignUpForm {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String profilePicture;
    private String email;
    private String homeCity;
    private String homeState;
    private String phoneNumber;
}
