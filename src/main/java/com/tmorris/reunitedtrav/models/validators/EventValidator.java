package com.tmorris.reunitedtrav.models.validators;

import com.tmorris.reunitedtrav.models.Event;
import com.tmorris.reunitedtrav.models.enums.Status;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class EventValidator implements ConstraintValidator<ValidEvent, Event> {

    @Override
    public void initialize(ValidEvent constraintAnnotation) {

    }

    @Override
    public boolean isValid(Event event, ConstraintValidatorContext context) {
        if (event == null) {
            return true;
        }

        if (isEventTimeLineNotProvided(event)) {
            return false;
        }

        return event.getMinimumAmountOfPeople() < event.getMaximumAmountOfPeople()
                && event.getStartTime().isAfter(LocalDateTime.now())
                && event.getStartTime().isBefore(event.getEndTime());
    }

    private boolean isEventTimeLineNotProvided(Event event) {
        return event.getStatus().eventTimeRequired() &&
                (event.getStartTime() == null ||
                        event.getEndTime() == null);
    }
}
