package com.tmorris.reunitedtrav.models.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {EventValidator.class})
public @interface ValidEvent {
    String message() default "End date must be after begin date "
            + "The minimum amount of occupants must be smaller than the maximum amount of occupants";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
