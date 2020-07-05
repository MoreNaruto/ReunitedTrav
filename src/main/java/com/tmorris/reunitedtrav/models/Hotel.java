package com.tmorris.reunitedtrav.models;

import com.tmorris.reunitedtrav.models.converter.AddressConverter;
import com.tmorris.reunitedtrav.models.enums.Status;
import com.tmorris.reunitedtrav.models.validators.ValidDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

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

    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "uuid4", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false, unique = true)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID uuid;

    @NotNull(message = "Name of Hotel must be provided")
    @Size(min = 1, max = 200)
    private String name;

    @NotNull(message = "Please provide address")
    @Column(columnDefinition = "TEXT")
    @Convert(converter = AddressConverter.class)
    private Address address;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Type can not be null")
    private Status status;

    private LocalDateTime checkIn;

    private LocalDateTime checkOut;

    @PrePersist
    public void createUuid() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
    }
}
