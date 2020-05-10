package com.tmorris.reunitedtrav.models;


import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Traveler {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @NotNull(message = "First name is required")
    @Size(min = 1, max = 200)
    private String firstName;

    @NotNull(message = "Last name is required")
    @Size(min = 1, max = 200)
    private String lastName;

    @NotNull(message = "Email is required")
    @Size(min = 1, max = 500)
    private String email;

    @NotNull(message = "City is required")
    @Size(min = 1, max = 200)
    private String homeCity;

    @NotNull(message = "State is required")
    @Size(min = 1, max = 200)
    private String homeState;

    private String profilePicture;

    @OneToMany
    private List<Itinerary> itineraries;

    @ManyToMany
    private List<Family> families;

    @CreationTimestamp
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    private LocalDateTime updateDateTime;
}
