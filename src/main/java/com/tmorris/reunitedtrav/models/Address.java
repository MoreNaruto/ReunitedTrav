package com.tmorris.reunitedtrav.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @NotNull(message = "A street needs to be provided")
    private String streetOne;

    private String streetTwo;

    private String apartmentNumber;

    @NotNull(message = "A city needs to be provided")
    private String city;

    @NotNull(message = "A state needs to be provided")
    private String state;

    @NotNull(message = "A zipcode needs to be provided")
    private String zipCode;
}
