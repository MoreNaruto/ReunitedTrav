package com.tmorris.reunitedtrav.controllers.jsonbodies;

import com.google.gson.annotations.Expose;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
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
    private LocalDateTime birthday;
}
