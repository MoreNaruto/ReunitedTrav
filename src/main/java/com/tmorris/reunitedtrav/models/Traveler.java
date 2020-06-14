package com.tmorris.reunitedtrav.models;


import com.tmorris.reunitedtrav.models.validators.ValidPhoneNumber;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Traveler {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID uuid;

    @NotNull(message = "First name is required")
    @Size(min = 1, max = 200)
    private String firstName;

    @NotNull(message = "Last name is required")
    @Size(min = 1, max = 200)
    private String lastName;

    @NotNull(message = "Email is required")
    @Size(min = 1, max = 500)
    private String email;

    @NotNull(message = "Phone number is required")
    @ValidPhoneNumber
    private String phoneNumber;

    @NotNull(message = "City is required")
    @Size(min = 1, max = 200)
    private String homeCity;

    @NotNull(message = "State is required")
    @Size(min = 1, max = 200)
    private String homeState;

    @OneToOne
    @NotNull
    private Account account;

    private String profilePicture;

    @CreationTimestamp
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    private LocalDateTime updateDateTime;

    @PrePersist
    public void createUuid() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
    }
}
