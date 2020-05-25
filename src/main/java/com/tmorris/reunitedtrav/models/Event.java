package com.tmorris.reunitedtrav.models;

import com.tmorris.reunitedtrav.models.enums.Type;
import com.tmorris.reunitedtrav.models.validators.ValidEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ValidEvent
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID uuid;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Type can not be null")
    private Type type;

    @NotNull(message = "A name must be provided")
    @Size(min = 1, max = 200)
    private String name;

    @NotNull(message = "A description needs to be provided")
    @Column(length = 100000, columnDefinition = "LONGTEXT")
    private String description;

    @NotNull(message = "Need to provide the minimum amount of occupants")
    @Min(value = 0, message = "An occupant is required")
    @Max(value = 10000, message = "Too many occupants for one event")
    private Integer minimumAmountOfPeople;

    @NotNull(message = "Need to provide the maximum amount of occupants")
    @Min(value = 0, message = "An occupant is required")
    @Max(value = 10000, message = "Too many occupants for one event")
    private Integer maximumAmountOfPeople;

    @ElementCollection
    private List<String> images;

    @NotNull(message = "A start date and time is required")
    private LocalDateTime startTime;

    @NotNull(message = "A end date and time is required")
    private LocalDateTime endTime;

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
