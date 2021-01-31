package com.tmorris.reunitedtrav.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    public static final String ROLE_ADMIN = "Admin";
    public static final String ROLE_AGENT = "Agent";
    public static final String ROLE_TRAVELER = "Traveler";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;

    @Column(unique = true)
    private String name;
}
