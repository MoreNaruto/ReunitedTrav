package com.tmorris.reunitedtrav.controllers.jsonbodies;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AgentSignUpForm {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String profilePicture;
    private String email;
    private String phoneNumber;
}
