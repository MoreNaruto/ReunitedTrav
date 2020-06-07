package com.tmorris.reunitedtrav.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "uuid4", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID uuid;

    @NotNull(message = "username is required")
    @Size(min = 1, max = 200)
    private String username;

    @NotNull(message = "password is required")
    @Size(min = 1, max = 200)
    private String password;

    @PrePersist
    public void createUuid() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
    }
}
