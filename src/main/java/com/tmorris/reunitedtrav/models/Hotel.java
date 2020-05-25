package com.tmorris.reunitedtrav.models;

import com.tmorris.reunitedtrav.models.validators.ValidDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

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
@ValidDateTime(startTime = "checkIn", endTime = "checkOut")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID uuid;

    @NotNull(message = "Name of Hotel must be provided")
    @Size(min = 1, max = 200)
    private String name;

    @NotNull(message = "A check in time must be provided")
    private LocalDateTime checkIn;

    @NotNull(message = "A check out time must be provided")
    private LocalDateTime checkOut;

    @PrePersist
    public void createUuid() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
    }
}
