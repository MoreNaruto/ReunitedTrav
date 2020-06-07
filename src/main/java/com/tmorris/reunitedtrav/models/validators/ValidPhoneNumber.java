package com.tmorris.reunitedtrav.models.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {PhoneNumberValidator.class})
@Documented
public @interface ValidPhoneNumber {
    String message() default "This is an invalid phone number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
