package com.tmorris.reunitedtrav.models.validators;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {
    @Override
    public void initialize(ValidPhoneNumber constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        PhoneNumber phoneNumber;
        try {
            phoneNumber = phoneNumberUtil.parse(value, "en_US");
        } catch (NumberParseException e) {
            e.printStackTrace();
            return false;
        }
        return phoneNumberUtil.isValidNumber(phoneNumber);
    }
}
