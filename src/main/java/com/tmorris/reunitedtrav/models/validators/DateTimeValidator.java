package com.tmorris.reunitedtrav.models.validators;

import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.time.LocalDateTime;

public class DateTimeValidator implements ConstraintValidator<ValidDateTime, Object> {

    private String startTime;
    private String endTime;

    @Override
    public void initialize(ValidDateTime constraint) {
        startTime = constraint.startTime();
        endTime = constraint.endTime();
    }

    @SneakyThrows
    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        LocalDateTime startTimeValue = (LocalDateTime) getFieldValue(object, startTime);
        LocalDateTime endTimeValue = (LocalDateTime) getFieldValue(object, endTime);

        return startTimeValue.isAfter(LocalDateTime.now()) && startTimeValue.isBefore(endTimeValue);
    }

    private Object getFieldValue(Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Class<?> clazz = object.getClass();
        Field timeField = clazz.getDeclaredField(fieldName);
        timeField.setAccessible(true);
        return timeField.get(object);
    }
}
