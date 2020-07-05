package com.tmorris.reunitedtrav.models;

import com.tmorris.reunitedtrav.models.enums.EventType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventTest {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    void shouldReturnInvalidDate() {
        Event event = Event.builder()
                .name("Bob's Burger")
                .eventType(EventType.CAMPING)
                .minimumAmountOfPeople(2)
                .maximumAmountOfPeople(10)
                .images(List.of())
                .description("Bob eats all the burgers")
                .startTime(LocalDateTime.now().plusDays(10))
                .endTime(LocalDateTime.now().plusDays(8))
                .build();

        Set<ConstraintViolation<Event>> violations = validator.validate(event);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldReturnInvalidOccupants() {
        Event event = Event.builder()
                .name("Bob's Burger")
                .eventType(EventType.CAMPING)
                .minimumAmountOfPeople(10)
                .maximumAmountOfPeople(2)
                .images(List.of())
                .description("Bob eats all the burgers")
                .startTime(LocalDateTime.now().plusDays(7))
                .endTime(LocalDateTime.now().plusDays(8))
                .build();

        Set<ConstraintViolation<Event>> violations = validator.validate(event);

        assertEquals(1, violations.size());
    }
}
