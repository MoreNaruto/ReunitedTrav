package com.tmorris.reunitedtrav.models.converter;

import com.google.gson.Gson;
import com.tmorris.reunitedtrav.models.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AddressConverterTest {
    private AddressConverter addressConverter;
    private Address address;
    private Gson gson;

    @BeforeEach
    void setUp() {
        addressConverter = new AddressConverter();
        address = Address.builder()
                .apartmentNumber("111")
                .city("Chicago")
                .state("IL")
                .country("US")
                .streetOne("123 N. State")
                .zipCode("60601")
                .build();
        gson = new Gson();
    }

    @Test
    void convertToDatabaseColumn() {
        String expectedJsonString = gson.toJson(address);

        String addressString = addressConverter.convertToDatabaseColumn(address);

        assertThat(expectedJsonString).isEqualTo(addressString);
    }

    @Test
    void convertToEntityAttribute() {
        Address actualAddress = addressConverter.convertToEntityAttribute(gson.toJson(address));

        assertThat(address).isEqualTo(actualAddress);
    }
}
