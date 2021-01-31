package com.tmorris.reunitedtrav.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tmorris.reunitedtrav.models.converter.AddressConverter;
import com.tmorris.reunitedtrav.models.enums.EventType;
import com.tmorris.reunitedtrav.models.enums.Status;
import com.tmorris.reunitedtrav.models.validators.ValidEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;

    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "uuid4", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false, unique = true)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID uuid;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Type can not be null")
    private EventType eventType;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status can not be null")
    private Status status;

    @NotNull(message = "A name must be provided")
    @Size(min = 1, max = 200)
    private String name;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = AddressConverter.class)
    private Address address;

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

    private Integer currentNumberOfOccupants;

    private Boolean adultOnly;

    @Column(length = 100000, columnDefinition = "LONGTEXT")
    private String notes;

    @ElementCollection
    private List<String> images;

    private LocalDateTime startTime;

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
