package com.telran.phonebookapi.validation.annotation;

import com.telran.phonebookapi.dto.PhoneDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, PhoneDto> {

    int minValue;
    int maxValue;

    @Override
    public void initialize(ValidPhoneNumber constraintAnnotation) {
        minValue = constraintAnnotation.minValue();
        maxValue = constraintAnnotation.maxValue();
    }

    @Override
    public boolean isValid(PhoneDto phoneDto, ConstraintValidatorContext constraintValidatorContext) {

        String number = String.valueOf(phoneDto.phoneNumber);
        int numberLength = number.length();
        return numberLength != 0 && numberLength < maxValue && numberLength > minValue;
    }
}
