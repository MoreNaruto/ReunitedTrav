package com.tmorris.reunitedtrav.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class HotelTest {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    void shouldReturnInvalidDate() {
        Hotel hotel = Hotel.builder()
                .name("The La Croix Resort")
                .checkIn(LocalDateTime.now().plusDays(6))
                .checkOut(LocalDateTime.now().plusDays(4))
                .build();

        Set<ConstraintViolation<Hotel>> violations = validator.validate(hotel);

        assertEquals(1, violations.size());
    }
}
